package iroz.backend.db.repository;

import iroz.backend.db.entity.UserStudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStudyRoomRepository extends JpaRepository<UserStudyRoom,Long> {
}
