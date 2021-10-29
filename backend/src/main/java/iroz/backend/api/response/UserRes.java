package iroz.backend.api.response;

import iroz.backend.db.entity.Roletype;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRes {
    String userId;
    String nickname;
    int generation;
    int classNum;
    Roletype position;
}
