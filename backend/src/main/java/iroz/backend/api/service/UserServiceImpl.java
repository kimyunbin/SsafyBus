package iroz.backend.api.service;

import iroz.backend.api.request.AnswerPostReq;
import iroz.backend.api.request.QuestionPostReq;
import iroz.backend.api.request.UserRegisterPostReq;
import iroz.backend.db.Mapping.AnonymousMapping;
import iroz.backend.db.Mapping.UserMapping;
import iroz.backend.db.entity.Anonymous;
import iroz.backend.db.entity.User;
import iroz.backend.db.repository.AnonymousRepository;
import iroz.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AnonymousRepository anonymousRepository;

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

    @Override

    public List<UserMapping> getUserByNickname(String nickname) {
        return userRepository.findAllByNickname(nickname);
    }
    public List<UserMapping> findAll(){
        return userRepository.findAllBy();
    }

    @Override
    public List<AnonymousMapping> findQuestion(String id){
        User user = userRepository.findByUserId(id).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });
        return anonymousRepository.findByUser(user);
    }

    @Override
    public Anonymous questionSave(String id, QuestionPostReq questionPostReq){
        User user = userRepository.findByUserId(id).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });
        Anonymous anonymous = Anonymous.builder().user(user).content(questionPostReq.getContent()).build();
        return anonymousRepository.save(anonymous);
    }

    @Override
    public void answerSave(String user_id, Long qna_pk, AnswerPostReq answerPostReq){
        Anonymous anonymous = anonymousRepository.findById(qna_pk).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });
        anonymous.setAnswer(answerPostReq.getAnswer());
        anonymousRepository.save(anonymous);
    }
}
