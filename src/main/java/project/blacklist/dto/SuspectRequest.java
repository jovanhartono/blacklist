package project.blacklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.blacklist.model.Post;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SuspectRequest {
    private String name;
    private String phoneNumber;
    private List<Post> post;
}
