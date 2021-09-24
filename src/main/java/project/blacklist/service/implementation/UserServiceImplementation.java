package project.blacklist.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.blacklist.dao.UserRepository;
import project.blacklist.dto.AppUser;
import project.blacklist.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public AppUser getUser(Long id) {
        Optional<AppUser> user = userRepository.getAppUserById(id);
        return user.orElse(null);
    }

    @Override
    public void registerUser(String username, Long phoneNumber, String password, String email) throws IllegalStateException{
        Optional<AppUser> userByEmail = userRepository.getAppUserByEmail(email);
        Optional<AppUser> userByPhoneNumber = userRepository.getAppUserByPhoneNumber(phoneNumber);
        Optional<AppUser> userByUsername = userRepository.getAppUserByUsername(username);

        if (userByEmail.isPresent() || userByUsername.isPresent() || userByPhoneNumber.isPresent()){
            String delimiter = " | ";
            String exceptionMessage = "already exist!";
            if (userByEmail.isPresent()){
                exceptionMessage = "email" + delimiter + exceptionMessage;
            }
            if (userByPhoneNumber.isPresent()){
                exceptionMessage = "number" + delimiter + exceptionMessage;
            }
            if(userByUsername.isPresent()){
                exceptionMessage = "username" + delimiter + exceptionMessage;
            }

            throw new IllegalStateException(exceptionMessage);
        }
        else{
            AppUser appUser = AppUser.builder()
                    .username(username).email(email).password(password).phoneNumber(phoneNumber)
                    .build();

            userRepository.save(appUser);
        }
    }
}
