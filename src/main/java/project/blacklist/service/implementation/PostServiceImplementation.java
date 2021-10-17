package project.blacklist.service.implementation;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.blacklist.dto.PostRequest;
import project.blacklist.model.AppUser;
import project.blacklist.model.Post;
import project.blacklist.model.PostImage;
import project.blacklist.repository.ImageRepository;
import project.blacklist.repository.PostRepository;
import project.blacklist.repository.UserRepository;
import project.blacklist.service.PostService;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    public static final String DIRECTORY = "/Users/jovan/Documents/Website/post-image";

    @Autowired
    public PostServiceImplementation(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public void createPost(PostRequest postRequest, String email, List<MultipartFile> postImages) throws IOException {
        String title = postRequest.getTitle();
        String description = postRequest.getDescription();
        LocalDateTime createdAt = LocalDateTime.now();
        Integer defaultLike = 0;

        Optional<AppUser> appUser = this.userRepository.getAppUserByEmail(email);
        if(appUser.isPresent()){
            Post post = Post.builder().title(title).description(description).createdAt(createdAt)
                        .likeCount(defaultLike).appUser(appUser.get()).build();
            this.postRepository.save(post);

            if(postImages != null){
                List<String> imageNames = new ArrayList<>();
                for (MultipartFile image: postImages){
                    String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                    Path imageStorage = get(DIRECTORY).toAbsolutePath().normalize().resolve(imageName);
                    copy(image.getInputStream(), imageStorage, REPLACE_EXISTING);
                    imageNames.add(imageName);
                }

                List<PostImage> imagesList = new ArrayList<>();
                for (String imageName: imageNames){
                    PostImage postImage = PostImage.builder().image(imageName).post(post).build();
                    imagesList.add(postImage);
                }
                this.imageRepository.saveAll(imagesList);
            }
        }


    }

    @Override
    public void deletePost(Long postId) throws NotFoundException {
        Optional<Post> getPost = this.postRepository.getPostByPostID(postId);
        Post post = getPost.orElseThrow(() -> new NotFoundException("Post not found!"));

        this.postRepository.deletePostByPostID(post.getPostID());
    }
}
