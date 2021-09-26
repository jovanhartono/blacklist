package project.blacklist.dto;

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
    private String phoneNumber;
    private String password;
    private String email;

    public String getUsername(){
        return this.username;
    }
}
