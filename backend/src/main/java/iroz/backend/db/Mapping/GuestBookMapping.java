package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface GuestBookMapping {
    UserInfoMapping getUser();
    String getContent();
    LocalDateTime getCreatedAt();
}
