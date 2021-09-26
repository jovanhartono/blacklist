package project.blacklist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @NotNull(message = "Post title cannot be empty or null!")
    private String title;
    @NotNull(message = "Post description cannot be empty or null!")
    @Lob
    private String description;
    private LocalDateTime createdAt;
    private Integer likeCount = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private AppUser appUser;
}
