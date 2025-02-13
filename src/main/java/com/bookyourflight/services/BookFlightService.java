package com.bookyourflight.services;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.models.User;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public Flight addFlight(@NotNull Flight flight, User user) throws InvalidFlightTimeException, UserNotFoundException{
        Flight updatedFlight = flightService.validateAndUpdateFlight(flight);

        updatedFlight = flightRepository.saveAndFlush(updatedFlight);
        updatedFlight.setUser(user);
        if (user.getFlightList() == null) {
            ArrayList<Flight> flightList = new ArrayList<>();
            flightList.add(updatedFlight);
            user.setFlightList(flightList);
        } else {
            user.getFlightList().add(updatedFlight);
        }
        user = userRepository.saveAndFlush(user);

        userRepository.save(user);
        flightRepository.save(updatedFlight);

        return updatedFlight;
    }


}
