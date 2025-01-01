package com.bookyourflight.services;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.models.User;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookFlightService {

    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final UserService userService;

    public BookFlightService(UserRepository userRepository, FlightRepository flightRepository, UserService userService) {
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.userService = userService;
    }

    public boolean isFlightAvailable(Flight flight) {
        // Verify that the time user tried to book a flight is earlier than flightTime
        Date currentDate = new Date();
        return flight.getFlightTime().after(currentDate);
    }

    public void validateFlightTime(Flight flight) {
        if (!isFlightAvailable(flight)) {
            throw new InvalidFlightTimeException("Flight must be earlier than the current date!");
        }
    }


    public User getAndValidateUser(String userName) {
        User user = userService.userExists(userName);
        
        if (user == null) {
            throw new UserNotFoundException("User Not Found!");
        } else {
            return user;
        }
    }

    public void addFlight(Flight flight) throws InvalidFlightTimeException, UserNotFoundException{
        User user = getAndValidateUser(flight.getUser().getName());
        validateFlightTime(flight);

        flight.setUser(user);
        user.getFlightList().add(flight);

        flightRepository.save(flight);
        userRepository.save(user);
    }


}
