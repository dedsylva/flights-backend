package com.bookyourflight.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class HomeFlight extends Flight {
    private int passengersLeft;

    public HomeFlight(Flight flight) {
        this.setId(flight.getId());
        this.setSource(flight.getSource());
        this.setDestination(flight.getDestination());
        this.setFlightTime(flight.getFlightTime());
        this.setPassengers(flight.getPassengers());
        this.setTotalCapacity(flight.getTotalCapacity());
        this.setPrice(flight.getPrice());
        this.setProfit(flight.getProfit());
        this.setUser(flight.getUser());

        this.passengersLeft = flight.getTotalCapacity() - flight.getPassengers();
    }

}
