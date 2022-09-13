package com.oopsmails.generaljava.retry.temp;

import com.oopsmails.model.RetryableException;

@FunctionalInterface
public interface RetryFunc {
    boolean run() throws RetryableException;
}
