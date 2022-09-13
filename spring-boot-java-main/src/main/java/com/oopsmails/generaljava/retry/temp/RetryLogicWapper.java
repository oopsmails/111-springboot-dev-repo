package com.oopsmails.generaljava.retry.temp;

import com.oopsmails.model.RetryableException;

public class RetryLogicWapper {
    int retryAttempts;
    final long TIME_TO_WAIT;

    RetryLogicWapper(int retryAttempts, long timeToWait) {
        this.retryAttempts = retryAttempts;
        this.TIME_TO_WAIT = timeToWait;
    }

    public void retryImpl(RetryFunc retryFunc) throws RetryableException {
        if (shouldRetry()) {
            retryAttempts--;
            retryFunc.run();
            waitBeforeNextRetry();
        } else {
            throw new RetryableException("RetryableException");
        }

    }
    public boolean shouldRetry() {
        return retryAttempts > 0;
    }
    public void waitBeforeNextRetry() {
        try {
            Thread.sleep(TIME_TO_WAIT);
        } catch (Exception e) {
        }
    }
}
