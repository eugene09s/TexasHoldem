package com.epam.poker.exception;

public class ServiceException extends Exception {
    public ServiceException () {
        super();
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
