package com.bookyourflight.controller;

import com.bookyourflight.exception.FlightWithFullCapacityException;
import com.bookyourflight.exception.NotEnoughBalanceException;
import com.bookyourflight.exception.UserNotFoundException;
import com.bookyourflight.models.BalanceRequest;
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
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment successfully! User " + flight.getUser().getName() + " is now booked for flight " + flight.getId());
        } catch (NotEnoughBalanceException | FlightWithFullCapacityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<String> addBalanceToUser(@RequestBody BalanceRequest balanceRequest) {
        try {
            flightPaymentService.addBalanceToUser(balanceRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Balance added to " + balanceRequest.getUser().getName());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

