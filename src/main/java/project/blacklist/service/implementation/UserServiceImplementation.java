package project.blacklist.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.model.Role;
import project.blacklist.repository.RoleRepository;
import project.blacklist.repository.UserRepository;
import project.blacklist.model.AppUser;
import project.blacklist.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public static final String DELIMITER = " | ";

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.getAppUserByEmail(email);
        AppUser user = appUser.orElseThrow(() -> new UsernameNotFoundException("No user " +
                "Found with username : " + email));
        Optional<Role> role = roleRepository.getRoleByRoleID(user.getRole().getRoleID());
        Role userRole = role.orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), getAuthorities(userRole.getType()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public void registerUser(String username, String phoneNumber, String password, String email) throws IllegalStateException, RoleNotFoundException{
        Optional<AppUser> userByEmail = userRepository.getAppUserByEmail(email);
        Optional<AppUser> userByPhoneNumber = userRepository.getAppUserByPhoneNumber(phoneNumber);
        Optional<AppUser> userByUsername = userRepository.getAppUserByUsername(username);
        Optional<Role> roleByType = roleRepository.getRoleByType("USER");
        Role userRole = roleByType.orElseThrow(() -> new RoleNotFoundException("Role finder error!"));

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
                    .role(userRole)
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
            catch (IllegalStateException | RoleNotFoundException e){
                throw new IllegalStateException(e);
            }
        }
    }
}
