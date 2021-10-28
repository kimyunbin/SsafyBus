package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface AnonymousMapping {
    Long getId();
    LocalDateTime getCreatedAt();
    String getContent();
    String getAnswer();
}
