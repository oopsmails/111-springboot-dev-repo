package com.oopsmails.springboot.mockbackend.exception;

public class OopsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OopsException(String message) {
        super(message);
    }

    public OopsException(String message, Throwable cause) {
        super(message, cause);
    }
}
