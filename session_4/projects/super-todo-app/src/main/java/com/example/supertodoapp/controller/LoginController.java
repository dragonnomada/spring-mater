package com.example.supertodoapp.controller;

import com.example.supertodoapp.entity.User;
import com.example.supertodoapp.entity.UserCredential;
import com.example.supertodoapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/generate/{count}")
    public Iterable<User> generateRandom(@PathVariable int count) {
        return userService.generateRandomUsers(count);
    }

    @PostMapping("/signIn")
    public ResponseEntity<User> signIn(@RequestBody UserCredential credentials) {

        try {
            User user = userService.signIn(credentials.getUsername(), credentials.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/signOut")
    public ResponseEntity<User> signOut(@RequestParam String token) {

        try {
            User user = userService.signOut(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/signOut/reset")
    public ResponseEntity<User> resetSignOut(@RequestParam String username) {

        try {
            User user = userService.resetSignOut(username);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

}
