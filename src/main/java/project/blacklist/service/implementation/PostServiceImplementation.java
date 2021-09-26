package project.blacklist.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.blacklist.model.Post;
import project.blacklist.repository.PostRepository;
import project.blacklist.service.PostService;

@Service
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImplementation(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void createPost() {
        Post post = Post.builder().build();
    }
}
