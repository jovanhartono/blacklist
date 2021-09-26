package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blacklist.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
