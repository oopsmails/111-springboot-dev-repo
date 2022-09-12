package com.oopsmails.generaljava.retry;

import java.util.Random;

public class RetryableMain {
    private static final int MAX_RETRIES = 5;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= MAX_RETRIES; i++) {
            try {
                // generate 0 or 1 with equal probability
                int zeroOrOne = new Random().nextInt(2);

                System.out.println("Random number is.. " + zeroOrOne);

                // 50% probability of getting java.lang.ArithmeticException: / by zero
                int rand = 1 / zeroOrOne;

                // don't retry on success
                break;
            } catch (Exception ex) {
                // handle exception
                System.out.println(ex.getMessage());    // log the exception

                // sleep for 1 seconds before retrying (Optional)
                Thread.sleep(1000);

                // throw exception if the last re-try fails
                if (i == MAX_RETRIES) {
                    throw ex;
                }
            }
        }
    }
}
