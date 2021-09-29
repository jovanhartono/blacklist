package project.blacklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.blacklist.dto.RegisterRequest;
import project.blacklist.model.AppUser;
import project.blacklist.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userList")
    public ResponseEntity<List<AppUser>> getAllUser(){
        return new ResponseEntity<>(this.userService.getAllAppUser(), HttpStatus.OK);
    }

    @PostMapping("/addUserList")
    public ResponseEntity<Void> insertUserList(@RequestBody List<RegisterRequest> userListRequest){
        this.userService.registerUserList(userListRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
