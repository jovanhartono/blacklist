package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.Post;
import project.blacklist.model.PostImage;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    Optional<List<PostImage>> getAllByPost(Post post);
    void deleteAllByPost(Post post);
}
