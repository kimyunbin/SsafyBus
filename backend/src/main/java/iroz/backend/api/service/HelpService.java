package iroz.backend.api.service;

import iroz.backend.api.request.HelpPostReq;
import iroz.backend.db.Mapping.CommentMapping;
import iroz.backend.db.Mapping.HelpAllMapping;
import iroz.backend.db.entity.Help;
import iroz.backend.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HelpService {
    Page<HelpAllMapping> findAll(Pageable pageable);
    void postHelp(User user, HelpPostReq helpPostReq);
    String deleteHelp(User user, Long help_pk);
    String putHelp(User user, Long Help_pk, HelpPostReq helpPostReq);
    String postComment(User user, Long help_pk, String content);
    Help getHelp(Long help_pk);
    List<CommentMapping> getComments(Long help_pk);
}
