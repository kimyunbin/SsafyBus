package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface HelpAllMapping {
    String getTitle();
    Long getId();
    UserMapping getUser();
    String getContent();
    LocalDateTime getCreatedAt();
}
