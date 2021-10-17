package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.Post;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getPostByPostID(Long postId);
    void deletePostByPostID(Long postId);
}
