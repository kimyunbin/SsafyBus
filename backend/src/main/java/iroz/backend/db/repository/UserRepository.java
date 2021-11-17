package iroz.backend.db.repository;

import iroz.backend.db.Mapping.UserMapping;
import iroz.backend.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    List<UserMapping> findAllByNicknameContaining(String nickname);
    Page<UserMapping> findAllBy(Pageable pageable);
}
