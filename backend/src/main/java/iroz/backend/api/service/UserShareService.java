package iroz.backend.api.service;

import iroz.backend.db.Mapping.UserShareMapping;
import iroz.backend.db.entity.User;
import iroz.backend.db.entity.UserShare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserShareService {

    Page<UserShareMapping> findShare(User user, Pageable pageable);

}
