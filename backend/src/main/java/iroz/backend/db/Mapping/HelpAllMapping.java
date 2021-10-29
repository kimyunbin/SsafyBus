package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface HelpAllMapping {
    String getTitle();
    String getContent();
    LocalDateTime getCreatedAt();
}
