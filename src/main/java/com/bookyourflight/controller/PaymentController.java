package com.bookyourflight.controller;

import com.bookyourflight.exception.FlightWithFullCapacityException;
import com.bookyourflight.exception.NotEnoughBalanceException;
import com.bookyourflight.models.Flight;
import com.bookyourflight.services.FlightPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/payments")
public class PaymentController {

    private final FlightPaymentService flightPaymentService;

    public PaymentController(FlightPaymentService flightPaymentService) {
        this.flightPaymentService = flightPaymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payFlight(@RequestBody Flight flight) {
        try {
            flightPaymentService.payFlight(flight);

            return ResponseEntity.status(HttpStatus.CREATED).body("Flight Added to " + flight.getUser().getName());
        } catch (NotEnoughBalanceException | FlightWithFullCapacityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

