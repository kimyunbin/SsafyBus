package iroz.backend.db.Mapping;

import iroz.backend.db.entity.User;

import java.time.LocalDateTime;

public interface StudyRoomRegiMapping {
    UserInfoMapping getUser();
    int getRoom();
    LocalDateTime getReservation();
    String getPassword();
}
