package com.bookyourflight.services;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.models.User;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class BookFlightService {

    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final UserService userService;
    private final FlightService flightService;

    public BookFlightService(UserRepository userRepository, FlightRepository flightRepository, UserService userService, FlightService flightService) {
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.userService = userService;
        this.flightService = flightService;
    }

    public void addFlight(@NotNull Flight flight) throws InvalidFlightTimeException, UserNotFoundException{
        User user = userService.getAndValidateUser(flight.getUser().getName());
        flightService.validateFlightTime(flight);

        flight.setUser(user);
        user.getFlightList().add(flight);

        flightRepository.save(flight);
        userRepository.save(user);
    }


}
