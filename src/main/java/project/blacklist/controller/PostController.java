package project.blacklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.blacklist.dto.PostRequest;
import project.blacklist.service.PostService;

import java.security.Principal;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest, Principal principal){
        this.postService.createPost(postRequest, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
