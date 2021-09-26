package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blacklist.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
