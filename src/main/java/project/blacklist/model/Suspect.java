package project.blacklist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Suspect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suspectID;
    @NotNull(message = "suspect name cannot be null or empty!")
    private String name;
    private String phoneNumber;
    private String accountPayment;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "suspect")
    private List<Post> post;
}
