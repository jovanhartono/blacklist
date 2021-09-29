package project.blacklist.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.repository.UserRepository;
import project.blacklist.model.AppUser;
import project.blacklist.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    public static final String DELIMITER = " | ";

    @Autowired
    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void loginUser(String email, String password) throws IllegalStateException{
        Optional<AppUser> userLogin = userRepository.getAppUserByEmail(email);
        String exceptionMessage = "not match!";
        if (userLogin.isEmpty()){
            throw new IllegalStateException("email " + exceptionMessage);
        }
        else{
            int isPasswordMatch = userLogin.get().getPassword().compareTo(password);
            if (isPasswordMatch == 0){
//                return userLogin.get();
            }
            else throw new IllegalStateException("password " + exceptionMessage);
        }
    }

    @Override
    public void registerUser(String username, String phoneNumber, String password, String email) throws IllegalStateException{
        Optional<AppUser> userByEmail = userRepository.getAppUserByEmail(email);
        Optional<AppUser> userByPhoneNumber = userRepository.getAppUserByPhoneNumber(phoneNumber);
        Optional<AppUser> userByUsername = userRepository.getAppUserByUsername(username);

        if (userByEmail.isPresent() || userByUsername.isPresent() || userByPhoneNumber.isPresent()){
            String exceptionMessage = "already exist!";
            if (userByEmail.isPresent()){
                exceptionMessage = "email" + DELIMITER + exceptionMessage;
            }
            if (userByPhoneNumber.isPresent()){
                exceptionMessage = "number" + DELIMITER + exceptionMessage;
            }
            if(userByUsername.isPresent()){
                exceptionMessage = "username" + DELIMITER + exceptionMessage;
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

    @Override
    public List<AppUser> getAllAppUser(){
        return userRepository.findAll();
    }

    @Override
    public void registerUserList(List<RegisterRequest> userList) throws IllegalStateException{
        for (RegisterRequest user : userList){
            try {
                this.registerUser(user.getUsername(), user.getPhoneNumber(), user.getPassword(), user.getEmail());
            }
            catch (IllegalStateException e){
                throw new IllegalStateException(e);
            }
        }
    }
}
