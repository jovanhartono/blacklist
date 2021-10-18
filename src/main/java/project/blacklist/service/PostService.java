package project.blacklist.service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.blacklist.dto.PostRequest;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {
    void createPost(PostRequest postRequest, String email, List<MultipartFile> images) throws IOException;
    void deletePost(Long postId) throws NotFoundException;
    void editPost(Long postId, PostRequest postRequest) throws NotFoundException;
}
