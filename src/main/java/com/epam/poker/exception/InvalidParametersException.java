package com.epam.poker.exception;

public class InvalidParametersException extends Exception{
    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(Throwable throwable) {
        super(throwable);
    }

    public InvalidParametersException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
