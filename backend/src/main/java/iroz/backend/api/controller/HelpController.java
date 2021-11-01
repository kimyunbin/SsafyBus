package iroz.backend.api.controller;

import iroz.backend.api.request.HelpPostReq;
import iroz.backend.api.request.QuestionPostReq;
import iroz.backend.api.service.HelpService;
import iroz.backend.common.auth.SsafyUserDetails;
import iroz.backend.common.model.response.BaseResponseBody;
import iroz.backend.db.Mapping.CommentMapping;
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
    public ResponseEntity<?> all(){
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

    @DeleteMapping("/{help_pk}")
    public ResponseEntity<? extends BaseResponseBody> deleteHelp(Authentication authentication, @PathVariable Long help_pk){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String msg = helpService.deleteHelp(userDetails.getUser(), help_pk);
        return ResponseEntity.status(200).body(BaseResponseBody.of(201, msg));
    }

    @PutMapping("/{help_pk}")
    public ResponseEntity<? extends BaseResponseBody> putHelp(Authentication authentication, @PathVariable Long help_pk, @RequestBody HelpPostReq helpPostReq){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String msg = helpService.putHelp(userDetails.getUser(), help_pk, helpPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(201, msg));
    }

    @GetMapping("/{help_pk}")
    public ResponseEntity getHelp(@PathVariable Long help_pk){
        HashMap map = new HashMap();
        Help result = helpService.getHelp(help_pk);
        List<CommentMapping> comments = helpService.getComments(help_pk);
        map.put("title",result.getTitle());
        map.put("content",result.getContent());
        map.put("code",result.getCode());
        map.put("link",result.getLink());
        map.put("comments",comments);
        map.put("user", result.getUser().getNickname());
        return ResponseEntity.ok().body(map);
    }

    @PostMapping("/{help_pk}")
    public ResponseEntity<? extends BaseResponseBody> postComment(Authentication authentication, @PathVariable Long help_pk, @RequestBody QuestionPostReq commentPostReq){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String msg = helpService.postComment(userDetails.getUser(), help_pk, commentPostReq.getContent());
        return ResponseEntity.status(200).body(BaseResponseBody.of(201, msg));
    }
}