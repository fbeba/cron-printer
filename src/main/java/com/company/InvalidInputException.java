package com.company;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String part, String value) {
        super(String.format("Invalid input for %s: '%s'", part, value));
    }
}
