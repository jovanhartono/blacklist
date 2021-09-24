package project.blacklist.service;

import org.springframework.stereotype.Service;
import project.blacklist.dto.AppUser;

@Service
public interface UserService {
    AppUser getUser(Long id);
    void registerUser(String username, Long phoneNumber, String password, String email);
}
