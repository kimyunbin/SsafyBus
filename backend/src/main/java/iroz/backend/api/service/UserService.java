package iroz.backend.api.service;

import iroz.backend.api.request.UserRegisterPostReq;
import iroz.backend.db.Mapping.UserMapping;
import iroz.backend.db.entity.User;

import java.util.List;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

public interface UserService {
    User createUser(UserRegisterPostReq userRegisterInfo);
    Optional<User> getUserByUserId(String userId);
    List<UserMapping> getUserByNickname(String nickname);
    List<UserMapping> findAll();
}
