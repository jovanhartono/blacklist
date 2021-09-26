package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
