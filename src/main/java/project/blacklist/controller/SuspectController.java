package project.blacklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.blacklist.dto.SuspectRequest;
import project.blacklist.service.SuspectService;

@RestController
@RequestMapping("/suspect")
public class SuspectController {

    private final SuspectService suspectService;

    @Autowired
    public SuspectController(SuspectService suspectService) {
        this.suspectService = suspectService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addSuspect(@RequestBody SuspectRequest suspectRequest){
        try {
            this.suspectService.createSuspect(suspectRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully adding suspect!");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add suspect!");
        }}
}
