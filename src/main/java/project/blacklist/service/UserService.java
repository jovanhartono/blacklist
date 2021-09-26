package project.blacklist.service;

import org.springframework.stereotype.Service;
import project.blacklist.model.AppUser;

@Service
public interface UserService {
    AppUser loginUser(String email, String password);
    void registerUser(String username, String phoneNumber, String password, String email);
}
