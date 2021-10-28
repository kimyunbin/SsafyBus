package iroz.backend.api.controller;

import iroz.backend.api.request.AnswerPostReq;
import iroz.backend.api.request.QuestionPostReq;
import iroz.backend.api.request.UserRegisterPostReq;
import iroz.backend.api.service.UserService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.db.Mapping.AnonymousMapping;
import iroz.backend.db.Mapping.UserMapping;
import iroz.backend.db.entity.Anonymous;
import iroz.backend.db.entity.User;
import iroz.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@RestController
@RequestMapping("/api-boot/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody UserRegisterPostReq registerInfo) {

        if (userRepository.findByUserId(registerInfo.getUserId()).isPresent()) {
            // 같은 id의 회원이 이미 존재하는 경우
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "already exist"));
        }

        if (registerInfo.getUserId().equals("") || registerInfo.getPassword().equals("") || registerInfo.getNickname().equals("")) {
            // 데이터 유효성검사 (수정해야함함)
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "invalidate data"));
        }

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        User user = userService.createUser(registerInfo);

        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "success"));
    }

    @PostMapping("/check")
    public ResponseEntity<? extends BaseResponseBody> idCheck(
            @RequestBody UserRegisterPostReq registerInfo) {

        if(userRepository.findByUserId(registerInfo.getUserId()).isPresent())  // 같은 id의 회원이 이미 존재하는 경우
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "already exist"));

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    @GetMapping("/profile")
    public ResponseEntity all(){
        List<UserMapping> result = userService.findAll();
        HashMap map = new HashMap();
        map.put("user",result);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity personal(@PathVariable String id){
        HashMap map = new HashMap();
        List<AnonymousMapping> result = userService.findQuestion(id);
        User user = userRepository.findByUserId(id).orElseThrow();
        map.put("nickname",user.getNickname());
        map.put("question",result);

        return ResponseEntity.ok().body(map);
    }

    @PostMapping("/question/{id}")
    public ResponseEntity<? extends BaseResponseBody> postQuestion(@PathVariable String id, @RequestBody QuestionPostReq questionPostReq){
        userService.questionSave(id, questionPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(201, "success"));
    }

    @PostMapping("/answer/{user_id}/{qna_pk}")
    public  ResponseEntity<? extends BaseResponseBody> postAnswer(@PathVariable String user_id, @PathVariable Long qna_pk, @RequestBody AnswerPostReq answerPostReq){
        userService.answerSave(user_id, qna_pk, answerPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(201, "success"));
    }
}