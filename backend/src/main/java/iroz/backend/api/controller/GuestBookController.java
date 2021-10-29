package iroz.backend.api.controller;


import iroz.backend.api.request.GuestBookSavePostReq;
import iroz.backend.api.service.GuestBookService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.common.util.LocalDateParser;
import iroz.backend.db.Mapping.GuestBookMapping;
import iroz.backend.db.Mapping.UserInfoMapping;
import iroz.backend.db.entity.GuestBook;
import iroz.backend.db.repository.GuestBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api-boot/guestbook")
public class GuestBookController {
    @Autowired
    GuestBookService guestBookService;

    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> register(Authentication authentication, @RequestBody GuestBookSavePostReq guestBookSavePostReq) {
        if (guestBookSavePostReq.getContent().equals("")) {
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "invalidate data"));
        }

        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        guestBookService.saveGuestBook(userDetails.getUser(), guestBookSavePostReq);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "success"));
    }
    @GetMapping()
    public ResponseEntity getListByGuestBook(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam String date) {
        LocalDateParser localDateParser = new LocalDateParser(date);
        List<GuestBookMapping> result = guestBookService.getGuestBookByDate(localDateParser.startDate(), localDateParser.endDate());
        HashMap map = new HashMap();
        map.put("guestbook",result);
        return ResponseEntity.ok().body(map);

    }



    


}
