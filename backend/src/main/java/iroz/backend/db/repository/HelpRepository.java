package iroz.backend.db.repository;

import iroz.backend.db.Mapping.HelpAllMapping;
import iroz.backend.db.entity.Help;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRepository extends JpaRepository<Help, Long> {
    Page<HelpAllMapping> findAllBy(Pageable pageable);
}
