package project.blacklist.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.blacklist.dto.PostRequest;
import project.blacklist.model.AppUser;
import project.blacklist.model.Post;
import project.blacklist.repository.PostRepository;
import project.blacklist.repository.UserRepository;
import project.blacklist.service.PostService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImplementation(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createPost(PostRequest postRequest) {
        String title = postRequest.getTitle();
        String description = postRequest.getDescription();
        LocalDateTime createdAt = LocalDateTime.now();
        Integer defaultLike = 0;
        Long userID = (long) 1;

        Optional<AppUser> appUser = this.userRepository.findById(userID);
        if(appUser.isPresent()){
            Post post = Post.builder().title(title).description(description).createdAt(createdAt)
                        .likeCount(defaultLike).appUser(appUser.get()).build();
            this.postRepository.save(post);
        }

    }
}
