package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blacklist.model.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
