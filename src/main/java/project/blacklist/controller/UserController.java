package project.blacklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.blacklist.dto.LoginRequest;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest appUserLogin){
        try{
            this.userService.loginUser(appUserLogin.getEmail(), appUserLogin.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest appUserRegistered){
        try{
            this.userService.registerUser(
                    appUserRegistered.getUsername(),
                    appUserRegistered.getPhoneNumber(),
                    appUserRegistered.getPassword(),
                    appUserRegistered.getEmail());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}