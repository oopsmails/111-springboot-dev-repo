package com.oopsmails.springboot.mockbackend.completablefuture;

import com.oopsmails.springboot.mockbackend.exception.OopsException;

public interface CompletableFutureExceptionHandler {
    OopsException handle(Throwable throwable);
}
