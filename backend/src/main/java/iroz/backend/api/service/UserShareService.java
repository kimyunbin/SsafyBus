package iroz.backend.api.service;

import iroz.backend.db.Mapping.UserShareMapping;
import iroz.backend.db.entity.User;
import iroz.backend.db.entity.UserShare;

import java.util.List;

public interface UserShareService {

    List<UserShareMapping> findshare(User user);

}
