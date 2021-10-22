package iroz.backend.api.service;

import iroz.backend.api.request.UserRegisterPostReq;
import iroz.backend.db.entity.User;
import iroz.backend.db.repository.UserRepository;
import iroz.backend.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = new User();
        user.setUserId(userRegisterInfo.getUserId());
        user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
        user.setClassNum(userRegisterInfo.getClassNum());
        user.setGeneration(userRegisterInfo.getGeneration());
        user.setNickname(userRegisterInfo.getNickname());
        user.setPosition(userRegisterInfo.getPosition());
        return userRepository.save(user);
    }
    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);

    }
}
