package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.PostImage;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
