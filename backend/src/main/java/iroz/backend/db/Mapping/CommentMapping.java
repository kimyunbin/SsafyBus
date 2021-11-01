package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface CommentMapping {
    UserInfoMapping getUser();
    String getContent();
    LocalDateTime getCreatedAt();
}
