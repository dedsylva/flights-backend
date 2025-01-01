package com.bookyourflight.repository;

import com.bookyourflight.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, String> {
    // Find all flights by source and destination
    List<Flight> findBySourceAndDestination(String source, String destination);

    // Find all flights on a specific date
    @Query("SELECT f FROM Flight f WHERE DATE(f.flightTime) = :flightDate")
    List<Flight> findFlightsByDate(@Param("flightDate") Date flightDate);

    // Find all flights with available passenger capacity
    @Query("SELECT f FROM Flight f WHERE f.passengers < :maxCapacity")
    List<Flight> findFlightsWithCapacity(@Param("maxCapacity") int maxCapacity);
}
