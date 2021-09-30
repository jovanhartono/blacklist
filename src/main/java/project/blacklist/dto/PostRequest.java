package project.blacklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.blacklist.model.Suspect;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostRequest {
    private String title;
    private String description;
    private List<Suspect> suspect;
}
