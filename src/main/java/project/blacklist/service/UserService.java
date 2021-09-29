package project.blacklist.service;

import org.springframework.stereotype.Service;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.model.AppUser;

import java.util.List;

@Service
public interface UserService {
    void loginUser(String email, String password);
    void registerUser(String username, String phoneNumber, String password, String email);
    List<AppUser> getAllAppUser();
    void registerUserList(List<RegisterRequest> userList);
}
