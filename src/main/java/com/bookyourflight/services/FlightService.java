package com.bookyourflight.services;

import com.bookyourflight.exception.FullFlightException;
import com.bookyourflight.exception.InvalidFlightTimeException;
import com.bookyourflight.models.Flight;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FlightService {

    public boolean isFlightAvailable(Flight flight) {
        // Verify that the time user tried to book a flight is earlier than flightTime
        Date currentDate = new Date();
        boolean isAvailable = flight.getFlightTime().after(currentDate);
        return isAvailable;
    }

    public Flight validateAndUpdateFlight(Flight flight) {
        if (!isFlightAvailable(flight)) {
            throw new InvalidFlightTimeException("Flight must be earlier than the current date!");
        }

        if (flightIsFull(flight)) {
            throw new FullFlightException("Flight is already full!");
        } else {
            int newPassengers = flight.getPassengers() + 1;
            int newPassengersLeft = flight.getPassengersLeft() - 1;
            flight.setPassengersLeft(newPassengersLeft);
            flight.setPassengers(newPassengers);
        }

        return flight;

    }

    public boolean flightIsFull(Flight flight) {
        return flight.getPassengers() >= flight.getTotalCapacity();
    }

}
