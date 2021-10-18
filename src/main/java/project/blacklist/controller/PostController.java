package project.blacklist.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.blacklist.dto.PostRequest;
import project.blacklist.service.PostService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

import static java.nio.file.Paths.get;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    public static final String DIRECTORY = "/Users/jovan/Documents/Website/post-image";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestParam("title") String title,
                                                   @RequestParam("description") String description,
                                                   @RequestParam(value = "post-images", required = false) List<MultipartFile> postImages,
                                                   Principal principal) throws IOException {
        PostRequest postRequest = PostRequest.builder().title(title).description(description).build();
        this.postService.createPost(postRequest, principal.getName(), postImages);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post Created!");
    }

    @GetMapping("/get-image/{imagename}")
    public ResponseEntity<Resource> getImages(@PathVariable("imagename") String imageName) throws IOException{
        Path imagePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(imageName);
        if(!Files.exists(imagePath)) {
            throw new FileNotFoundException(imageName + " was not found on the server");
        }
        Resource resource = new UrlResource(imagePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Image-Name", imageName);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;Image-Name=" + resource.getFilename());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(imagePath)))
                .headers(httpHeaders).body(resource);
    }

    @DeleteMapping("/delete/{post-id}")
    public ResponseEntity<String> deletePost(@PathVariable("post-id") Long postId) {
        try {
            this.postService.deletePost(postId);
            return new ResponseEntity<>("Post successfully deleted.", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{post-id}")
    public ResponseEntity<String> editPost (@PathVariable("post-id") Long postId, @RequestBody PostRequest postRequest){
        try{
            this.postService.editPost(postId, postRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Edit post success!");
        }
        catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
