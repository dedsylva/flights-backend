package com.bookyourflight.services;

import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.models.Flight;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FlightService {

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

    public boolean flightIsFull(Flight flight) {
        return flight.getPassengers() >= flight.getTotalCapacity();
    }

}
