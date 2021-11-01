package iroz.backend.db.repository;

import iroz.backend.db.Mapping.CommentMapping;
import iroz.backend.db.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentMapping> findAllByHelpId(Long helpId);
}
