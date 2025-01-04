package com.bookyourflight.services;

import com.bookyourflight.exception.FlightWithFullCapacityException;
import com.bookyourflight.exception.NotEnoughBalanceException;
import com.bookyourflight.models.BalanceRequest;
import com.bookyourflight.models.Flight;
import com.bookyourflight.models.User;
import com.bookyourflight.repository.FlightRepository;
import com.bookyourflight.repository.UserRepository;

import java.math.BigDecimal;

public class FlightPaymentService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final FlightService flightService;
    private final FlightRepository flightRepository;

    public FlightPaymentService(UserService userService, FlightService flightService, FlightRepository flightRepository, UserRepository userRepository) {
        this.userService = userService;
        this.flightService = flightService;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    public void payFlight(Flight flight) {
        User user = userService.getAndValidateUser(flight.getUser().getName());

        // validate if user has enough balance
        if (!userService.validateUserBalance(user, flight.getPrice())) {
            throw new NotEnoughBalanceException("User " + user.getName() + "without balance for flight. Balance is " + user.getBalance() + "and the price of flight is " + flight.getPrice());
        };

        // validate if flight has enough capacity
        if (flightService.flightIsFull(flight)) {
            throw new FlightWithFullCapacityException("Flight is already with full capacity!");
        }

        BigDecimal priceAsBigDecimal = BigDecimal.valueOf(flight.getPrice());

        // subtract balance from user
        BigDecimal newBalance = user.getBalance().subtract(priceAsBigDecimal);
        user.setBalance(newBalance);

        // adds profit for flight
        BigDecimal newProfit = flight.getProfit().add(priceAsBigDecimal);
        flight.setProfit(newProfit);

        // adds passenger
        flight.setPassengers(flight.getPassengers() + 1);

        flightRepository.save(flight);
        userRepository.save(user);
    }

    public void addBalanceToUser(BalanceRequest balanceRequest) {
        User user = userService.getAndValidateUser(balanceRequest.getUser().getName());
        user.setBalance(user.getBalance().add(balanceRequest.getAmount()));
        userRepository.save(user);
    }
}
