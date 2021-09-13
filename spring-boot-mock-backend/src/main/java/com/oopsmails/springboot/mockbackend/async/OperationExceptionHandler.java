package com.oopsmails.springboot.mockbackend.async;

import com.oopsmails.springboot.mockbackend.exception.OopsException;

public interface OperationExceptionHandler {
    OopsException handle(Throwable throwable);
}
