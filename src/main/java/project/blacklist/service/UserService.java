package project.blacklist.service;

import org.springframework.stereotype.Service;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.model.AppUser;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public interface UserService {
    void registerUser(String username, String phoneNumber, String password, String email) throws RoleNotFoundException;
    List<AppUser> getAllAppUser();
    void registerUserList(List<RegisterRequest> userList);
}
