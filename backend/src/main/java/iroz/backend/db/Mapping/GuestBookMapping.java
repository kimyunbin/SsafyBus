package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface GuestBookMapping {
    UserMapping getUser();
    String getContent();
    LocalDateTime getCreatedAt();
}
