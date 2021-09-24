package project.blacklist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterRequest {
    private String username;
    private Long phoneNumber;
    private String password;
    private String email;

    public String getUsername(){
        return this.username;
    }
}
