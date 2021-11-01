package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface HelpAllMapping {
    String getTitle();
    Long getId();
    UserInfoMapping getUser();
    String getContent();
    LocalDateTime getCreatedAt();
}
