package project.blacklist.service;

import org.springframework.stereotype.Service;
import project.blacklist.dto.PostRequest;

@Service
public interface PostService {
    void createPost(PostRequest postRequest);
}
