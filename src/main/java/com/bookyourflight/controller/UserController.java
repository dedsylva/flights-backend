package com.bookyourflight.controller;

import com.bookyourflight.models.User;
import com.bookyourflight.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {

    private final UserRepository userRepository; // Inject UserRepository to update user data

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{user}")
    public ResponseEntity<User> getUserByName(@PathVariable("user") String userName) {
        User user = userRepository.findByName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

}
