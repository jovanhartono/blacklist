package project.blacklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.blacklist.model.RegisterRequest;
import project.blacklist.service.UserService;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<String> getUserData(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<String> insertUserData(@RequestBody RegisterRequest appUserRegistered){
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
