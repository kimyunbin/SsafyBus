package iroz.backend.api.controller;

import iroz.backend.api.request.HelpPostReq;
import iroz.backend.api.service.HelpService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.db.Mapping.HelpAllMapping;
import iroz.backend.db.entity.Help;
import iroz.backend.db.repository.CommentRepository;
import iroz.backend.db.repository.HelpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api-boot/help")
public class HelpController {
    @Autowired
    private HelpService helpService;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public ResponseEntity all(){
        List<HelpAllMapping> result = helpService.findAll();
        HashMap map = new HashMap();
        map.put("help",result);
        return ResponseEntity.ok().body(map);
    }

    @PostMapping("")
    public ResponseEntity<? extends BaseResponseBody> postHelp(Authentication authentication, @RequestBody HelpPostReq helpPostReq){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        helpService.postHelp(userDetails.getUser(), helpPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(201, "success"));
    }
}
