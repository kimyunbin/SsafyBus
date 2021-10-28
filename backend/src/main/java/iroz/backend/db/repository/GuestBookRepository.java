package iroz.backend.db.repository;

import iroz.backend.db.Mapping.GuestBookMapping;
import iroz.backend.db.Mapping.UserInfoMapping;
import iroz.backend.db.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBook,Long> {
    List<GuestBookMapping> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
