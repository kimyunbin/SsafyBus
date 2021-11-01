package iroz.backend.db.Mapping;

import java.time.LocalDateTime;

public interface UserShareMapping {
    ShareMapping getShare();
    LocalDateTime getCreatedAt();
}
