package iroz.backend.api.controller;

import iroz.backend.api.request.StudyRoomRegisterPostReq;
import iroz.backend.api.service.StudyRoomService;
import iroz.backend.api.service.UserService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.db.entity.StudyRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api-boot/studyroom")
public class StudyRoomCotroller {
    @Autowired
    StudyRoomService studyRoomService;

    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> register(Authentication authentication, @Valid @RequestBody StudyRoomRegisterPostReq studyRoomRegisterPostReq) {
        // 등록한 사람이 등록시간에 중복으로 점유하고 있는지 체크
        // 해당 시간+ 방이 차 있는지 체크
        int year = studyRoomRegisterPostReq.getDate().getYear();
        int month = studyRoomRegisterPostReq.getDate().getMonthValue();
        int dayOfMonth = studyRoomRegisterPostReq.getDate().getDayOfMonth();
        int hour = studyRoomRegisterPostReq.getDate().getHour();
        int room = studyRoomRegisterPostReq.getRoom();
        List<StudyRoom> listGroupBy = studyRoomService.getListGroupBy(year, month, dayOfMonth, hour,room);
        if (listGroupBy.size()>0) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(403, "already exist"));
        }

        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();

        // 유저아이디 있는지 체크
        List userid = studyRoomRegisterPostReq.getUserid();
        for (int i = 0; i < userid.size(); i++) {
            if (userService.getUserByUserId(userid.get(i).toString()).isEmpty()) {
                return ResponseEntity.status(400).body(BaseResponseBody.of(403, "not users"));
            }
        }

        studyRoomService.register(userDetails.getUser(),studyRoomRegisterPostReq);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "success"));
    }

}
