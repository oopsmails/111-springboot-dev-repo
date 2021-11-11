package com.oopsmails.exceptionhandling.mutex.service;

public class OopsPessimisticLockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OopsPessimisticLockException(String message) {
        super(message);
    }

    public OopsPessimisticLockException(Throwable cause) {
        super(cause);
    }

    public OopsPessimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
