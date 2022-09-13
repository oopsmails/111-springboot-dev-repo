package com.oopsmails.model;

public class RetryableException extends Exception {

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(Exception exception) {
        super(exception);
    }
}
