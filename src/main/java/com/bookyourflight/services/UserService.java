package com.bookyourflight.services;

import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.User;
import com.bookyourflight.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userExists(String userName) {
        return userRepository.findByName(userName);
    }

    public User getAndValidateUser(String userName) {
        User user = userExists(userName);

        if (user == null) {
            throw new UserNotFoundException("User Not Found!");
        } else {
            return user;
        }
    }

    public boolean validateUserBalance(User user, Float price) {
        BigDecimal priceAsBigDecimal = BigDecimal.valueOf(price);
        return user.getBalance().compareTo(priceAsBigDecimal) >= 0;
    }


}
