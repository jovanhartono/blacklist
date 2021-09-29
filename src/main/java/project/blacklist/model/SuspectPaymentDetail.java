package project.blacklist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SuspectPaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountPaymentID;
    @NotNull(message = "bank cannot be null or empty!")
    private String bankType;
    @NotNull(message = "bank account cannot be null or empty!")
    private String bankAccountNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suspectID", referencedColumnName = "suspectID", nullable = false)
    private Suspect suspect;
}
