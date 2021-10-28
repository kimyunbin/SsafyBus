package iroz.backend.db.repository;

import iroz.backend.db.Mapping.AnonymousMapping;
import iroz.backend.db.entity.Anonymous;
import iroz.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnonymousRepository extends JpaRepository<Anonymous, Long> {
    List<AnonymousMapping> findByUser(User user);
}
