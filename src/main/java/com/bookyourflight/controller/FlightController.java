package com.bookyourflight.controller;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.models.HomeFlight;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.services.BookFlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/flights")
@CrossOrigin(origins = "http://localhost:5173/flights")
public class FlightController {

    private final FlightRepository flightRepository;
    private final BookFlightService bookFlightService;

    public FlightController(FlightRepository flightRepository, BookFlightService bookFlightService) {
        this.flightRepository = flightRepository;
        this.bookFlightService = bookFlightService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable("id") String id) {
        Optional<Flight> optionalFlight = flightRepository.findById(id);

        if (optionalFlight.isEmpty()) {
            // Return an error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Flight not found with ID: " + id);
        }

        // If present, return the flight
        Flight flight = optionalFlight.get();
        return ResponseEntity.ok(flight);

    }

    @GetMapping("/")
    public List<Flight> getAvailableFlights() {
        return flightRepository.findAll();
    }


    @GetMapping("/home")
    public List<HomeFlight> getAvailableHomeFlights() {
        List <Flight> flightList = getAvailableFlights();
        List<HomeFlight> homeFlightList = new ArrayList<>();

        for (Flight flight : flightList) {
            homeFlightList.add(new HomeFlight(flight));
        }
        return homeFlightList;
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
