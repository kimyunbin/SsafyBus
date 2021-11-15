package iroz.backend.api.controller;

import iroz.backend.api.request.StudyRoomRegisterPostReq;
import iroz.backend.api.request.StudyRoomfindGetReq;
import iroz.backend.api.service.StudyRoomService;
import iroz.backend.api.service.UserService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.common.util.LocalDateParser;
import iroz.backend.db.Mapping.StudyRoomRegiMapping;
import iroz.backend.db.entity.StudyRoom;
import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        System.out.println("-----------------");
        System.out.println("room = " + room);
        System.out.println("year = " + year);
        System.out.println("month = " + month);
        System.out.println("dayOfMonth = " + dayOfMonth);
        System.out.println("hour = " + hour);

        List<StudyRoom> listGroupBy = studyRoomService.getListGroupBy(year, month, dayOfMonth, hour, room);
        if (listGroupBy.size() > 0) {
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

        studyRoomService.register(userDetails.getUser(), studyRoomRegisterPostReq);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "success"));
    }

    @GetMapping("/check")
    public ResponseEntity findOpenRoom(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date, @RequestParam int room) {
        if (room >= 4 || room <= 0) {
            return ResponseEntity.badRequest().build();
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
        List<Object[]> result = studyRoomService.getListGroupByDay(year, month, dayOfMonth, room);

        HashMap map = new HashMap();
        for (int i = 0; i < 24; i++) {
            map.put(i, true);
        }
        for (Object[] obj : result) {
            String s = obj[3].toString();
            int substring = Integer.parseInt(s.substring(11, 13));
            map.replace(substring,false);
        }
        HashMap r = new HashMap();
        r.put("time",map);
        return ResponseEntity.ok().body(r);

    }

    @GetMapping()
    public ResponseEntity getkiosk(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam String date) {
        HashMap map = new HashMap();
        LocalDateParser localDateParser = new LocalDateParser(date);
        map.put(1,studyRoomService.findByRoomAndReservationBetween(1,localDateParser.startDate(), localDateParser.endDate()));
        map.put(2,studyRoomService.findByRoomAndReservationBetween(2,localDateParser.startDate(), localDateParser.endDate()));
        map.put(3,studyRoomService.findByRoomAndReservationBetween(3,localDateParser.startDate(), localDateParser.endDate()));
        HashMap r = new HashMap();
        r.put("kiosk",map);
        return ResponseEntity.ok().body(r);
    }

    @GetMapping("/{room}")
    public ResponseEntity getroom(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam String date, @PathVariable("room") int room) {
        if (room >= 4 || room <= 0) {
            return ResponseEntity.badRequest().build();
        }
        HashMap map = new HashMap();
        LocalDateParser localDateParser = new LocalDateParser(date);
        map.put(room,studyRoomService.findByRoomAndReservationBetween(room,localDateParser.startDate(), localDateParser.endDate()));
        HashMap r = new HashMap();
        r.put("room",map);
        return ResponseEntity.ok().body(r);

    }



}
