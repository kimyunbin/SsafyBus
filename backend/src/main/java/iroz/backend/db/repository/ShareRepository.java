package iroz.backend.db.repository;

import iroz.backend.db.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    Share findByPath(String path);
}
