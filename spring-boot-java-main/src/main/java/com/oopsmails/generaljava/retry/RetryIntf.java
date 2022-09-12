package com.oopsmails.generaljava.retry;
public interface RetryIntf {
    void run();
    void handleException(Exception ex);
}
