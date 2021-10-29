package iroz.backend.api.service;

import iroz.backend.api.request.HelpPostReq;
import iroz.backend.db.Mapping.HelpAllMapping;
import iroz.backend.db.entity.Help;
import iroz.backend.db.entity.User;

import java.util.List;

public interface HelpService {
    List<HelpAllMapping> findAll();
    void postHelp(User user, HelpPostReq helpPostReq);
}
