package com.bookyourflight.controller;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.models.User;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.repository.UserRepository;
import com.bookyourflight.services.BookFlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {

    private final UserRepository userRepository; // Inject UserRepository to update user data

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
