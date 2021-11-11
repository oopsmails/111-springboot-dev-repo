package com.oopsmails.exceptionhandling.mutex.service;

public class OopsOptimisticLockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OopsOptimisticLockException(String message) {
        super(message);
    }

    public OopsOptimisticLockException(Throwable cause) {
        super(cause);
    }

    public OopsOptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
