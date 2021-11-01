package iroz.backend.db.repository;

import iroz.backend.db.Mapping.GuestBookMapping;
import iroz.backend.db.entity.DietToday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DietTodayRepository extends JpaRepository<DietToday,Long> {
    List<DietToday> findAllByCreatedAtBetween(LocalDate start, LocalDate end);
}
