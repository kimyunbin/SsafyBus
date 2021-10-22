package iroz.backend.api.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import iroz.backend.db.entity.Roletype;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
public class UserRegisterPostReq {
    String userId;
    String password;
    String nickname;
    int generation;
    int classNum;
    Roletype position;
}