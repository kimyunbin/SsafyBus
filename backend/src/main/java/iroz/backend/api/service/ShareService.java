package iroz.backend.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import iroz.backend.api.request.ShareRegisterPostReq;
import iroz.backend.common.util.S3Component;
import iroz.backend.db.entity.Share;
import iroz.backend.db.entity.User;
import iroz.backend.db.entity.UserShare;
import iroz.backend.db.repository.ShareRepository;
import iroz.backend.db.repository.UserRepository;
import iroz.backend.db.repository.UserShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class ShareService {
    private final UploadService s3Service;
    private final AmazonS3 amazonS3;
    private final S3Component component;
    @Autowired
    ShareRepository shareRepository;

    @Autowired
    UserShareRepository userShareRepository;

    @Autowired
    UserRepository userRepository;

    public String uploadImage(MultipartFile file, ShareRegisterPostReq shareRegisterPostReq, User user) {
        String fileName = createFileName(file.getOriginalFilename());
        System.out.println(fileName);
        //aws input
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try (InputStream inputStream = file.getInputStream()) {
            s3Service.uploadFile(inputStream, objectMetadata, fileName);
            //db 넣기
            Share share = Share.builder().title(shareRegisterPostReq.getTitle()).user(user).path(fileName).original(file.getOriginalFilename()).build();
            shareRepository.save(share);
            //공유자
            UserShare u = UserShare.builder().share(share).user(user).build();
            userShareRepository.save(u);
            // 피공유자
            List list = shareRegisterPostReq.getUserid();
            for (int i = 0; i < list.size(); i++) {
                Optional<User> byUserId = userRepository.findByUserId(list.get(i).toString());
                UserShare userShare = UserShare.builder().user(byUserId.get()).share(share).build();
                userShareRepository.save(userShare);
            }


        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        return "success";
    }
    public ResponseEntity<byte[]> getObject(String storedFileName) throws IOException{
        S3Object o = amazonS3.getObject(new GetObjectRequest(component.getBucket(), storedFileName));
        S3ObjectInputStream objectInputStream = o.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        Share share = shareRepository.findByPath(storedFileName);

        String fileName = URLEncoder.encode(share.getOriginal(), "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);

    }


    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다.", filename));
        }
    }
}
