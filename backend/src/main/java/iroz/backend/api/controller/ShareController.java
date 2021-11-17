package iroz.backend.api.controller;

import iroz.backend.api.request.ShareRegisterPostReq;
import iroz.backend.api.service.ShareService;
import iroz.backend.api.service.UserService;
import iroz.backend.api.service.UserShareService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.db.Mapping.UserShareMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api-boot/sharefile")
public class ShareController {
    @Autowired
    ShareService shareService;
    @Autowired
    UserShareService userShareService;
    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> uploadImage(@RequestPart MultipartFile file, ShareRegisterPostReq shareRegisterPostReq, Authentication authentication) {
        // title 공백 체크
        if (shareRegisterPostReq.getTitle().equals("")) {
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "invalidate data"));
        }
        // 유저아이디 있는지 체크
        List userid = shareRegisterPostReq.getUserid();

        for (int i = 0; i < userid.size(); i++) {
            System.out.println(userid.get(i).toString());
            if (userService.getUserByUserId(userid.get(i).toString()).isEmpty()) {
                return ResponseEntity.status(403).body(BaseResponseBody.of(403, "not users"));
            }
        }
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String result = shareService.uploadImage(file, shareRegisterPostReq, userDetails.getUser());
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, result));
    }

    @GetMapping()
    public ResponseEntity getShare(Authentication authentication, @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Page<UserShareMapping> result = userShareService.findShare(userDetails.getUser(), pageable);
        HashMap map = new HashMap();
        map.put("sharefile",result);
        return ResponseEntity.ok().body(map);
    }
    @GetMapping("/{path}")
    @CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
    public ResponseEntity<byte[]> download(@PathVariable String path) throws IOException {
        return shareService.getObject(path);
    }
}
