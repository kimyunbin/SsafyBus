package iroz.backend.api.service;

import iroz.backend.db.Mapping.UserShareMapping;
import iroz.backend.db.entity.User;
import iroz.backend.db.entity.UserShare;
import iroz.backend.db.repository.UserShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserShareServiceImpl implements UserShareService {
    @Autowired
    UserShareRepository userShareRepository;
    @Override
    public List<UserShareMapping> findshare(User user) {
        return userShareRepository.findByUserOrderByCreatedAt(user);
    }
}
