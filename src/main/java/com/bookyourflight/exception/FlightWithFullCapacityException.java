package com.bookyourflight.exception;

public class FlightWithFullCapacityException extends RuntimeException {
    public FlightWithFullCapacityException(String message) {
        super(message);
    }
}
