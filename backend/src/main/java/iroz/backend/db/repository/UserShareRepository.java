package iroz.backend.db.repository;

import iroz.backend.db.Mapping.UserShareMapping;
import iroz.backend.db.entity.User;
import iroz.backend.db.entity.UserShare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserShareRepository extends JpaRepository<UserShare, Long> {
    Page<UserShareMapping> findByUserOrderByCreatedAt(User user, Pageable pageable);
}
