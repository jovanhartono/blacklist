package project.blacklist.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.repository.UserRepository;
import project.blacklist.model.AppUser;
import project.blacklist.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    public static final String DELIMITER = " | ";
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.getAppUserByEmail(email);
        AppUser user = appUser.orElseThrow(() -> new UsernameNotFoundException("No user " +
                "Found with username : " + email));
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
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
            String encodedPassword = passwordEncoder.encode(password);
            AppUser appUser = AppUser.builder()
                    .username(username)
                    .email(email)
                    .password(encodedPassword)
                    .phoneNumber(phoneNumber)
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
