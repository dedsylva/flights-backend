package com.bookyourflight.exception;

public class InvalidFlightTimeException extends RuntimeException {
    public InvalidFlightTimeException(String message) {
        super(message);
    }
}
