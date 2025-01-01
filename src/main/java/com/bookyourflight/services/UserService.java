package com.bookyourflight.services;

import com.bookyourflight.models.User;
import com.bookyourflight.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userExists(String userName) {
        return userRepository.findByName(userName);
    }

}
