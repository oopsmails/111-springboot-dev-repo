package com.oopsmails.generaljava.retry;

import java.util.Random;

public class RetryIntfMain {
    private static final int MAX_RETRIES = 5;

    public static void main(String[] args) {
        withMaxRetries(new RetryIntf() {
            @Override
            public void run() {
                // generate 0 or 1 with equal probability
                int zeroOrOne = new Random().nextInt(2);

                System.out.println("Random number is.. " + zeroOrOne);

                // 50% probability of getting java.lang.ArithmeticException: / by zero
                int rand = 1 / zeroOrOne;
            }

            @Override
            public void handleException(Exception ex) {
                System.out.println(ex.getMessage());    // log the exception

                try {
                    // sleep for 1 seconds before retrying (Optional)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());    // log the exception
                }
            }
        });
    }

    public static void withMaxRetries(RetryIntf retryFunc) {
        for (int i = 0; i <= MAX_RETRIES; i++) {
            try {
                retryFunc.run();
                break;                                    // don't retry on success
            } catch (Exception ex) {
                retryFunc.handleException(ex);

                // throw exception if the last re-try fails
                if (i == MAX_RETRIES) {
                    throw ex;
                }
            }
        }
    }
}
