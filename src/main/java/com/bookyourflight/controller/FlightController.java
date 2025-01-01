package com.bookyourflight.controller;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.repository.UserRepository;
import com.bookyourflight.services.BookFlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/flights")
public class FlightController {

    private final FlightRepository flightRepository;
    private final UserRepository userRepository; // Inject UserRepository to update user data
    private final BookFlightService bookFlightService;

    public FlightController(FlightRepository flightRepository, UserRepository userRepository, BookFlightService bookFlightService) {
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.bookFlightService = bookFlightService;
    }

    @GetMapping("")
    public List<Flight> getAvailableFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/by-route")
    public List<Flight> getFlightsBySourceAndDestination(@RequestParam String source, @RequestParam String destination) {
        return flightRepository.findBySourceAndDestination(source, destination);
    }

    @GetMapping("/by-date")
    public List<Flight> getFlightsByDate(@RequestParam Date flightDate) {
        return flightRepository.findFlightsByDate(flightDate);
    }

    @GetMapping("/with-capacity")
    public List<Flight> getFlightsWithCapacity(@RequestParam int maxCapacity) {
        return flightRepository.findFlightsWithCapacity(maxCapacity);
    }

    @PostMapping("")
    public ResponseEntity<String> addFlight(@RequestBody Flight flight) {
        try {
            bookFlightService.addFlight(flight);

            return ResponseEntity.status(HttpStatus.CREATED).body("Flight Added to " + flight.getUser().getName());
        } catch (InvalidFlightTimeException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
