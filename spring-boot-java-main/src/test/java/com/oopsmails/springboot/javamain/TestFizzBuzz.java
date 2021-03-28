package com.oopsmails.springboot.javamain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFizzBuzz {

    @Test
    public void testWithNumberIsDividableBy3() {
        assertEquals("Fizz", TestFizzBuzz.fizzBuzz(3));
        assertEquals("Fizz", TestFizzBuzz.fizzBuzzInJava8(3));
        assertEquals("Fizz", TestFizzBuzz.fizzBuzzSolutionJava8(3));
    }

    @Test
    public void testWithNumberIsDividableBy5() {
        assertEquals("Buzz", TestFizzBuzz.fizzBuzz(5));
        assertEquals("Buzz", TestFizzBuzz.fizzBuzzInJava8(5));
        assertEquals("Buzz", TestFizzBuzz.fizzBuzzSolutionJava8(5));
    }

    @Test
    public void testWithNumberIsDividableBy15() {
       assertEquals("FizzBuzz", TestFizzBuzz.fizzBuzz(15));
       assertEquals("FizzBuzz", TestFizzBuzz.fizzBuzzInJava8(15));
       assertEquals("FizzBuzz", TestFizzBuzz.fizzBuzzSolutionJava8(15));
       assertEquals("FizzBuzz", TestFizzBuzz.fizzBuzz(45));
       assertEquals("FizzBuzz", TestFizzBuzz.fizzBuzzInJava8(45));
       assertEquals("FizzBuzz", TestFizzBuzz.fizzBuzzSolutionJava8(45));
    }

    @Test
    public void testOtherNumbers() {
       assertEquals("1", TestFizzBuzz.fizzBuzz(1));
       assertEquals("1", TestFizzBuzz.fizzBuzzInJava8(1));
       assertEquals("1", TestFizzBuzz.fizzBuzzSolutionJava8(1));
       assertEquals("7", TestFizzBuzz.fizzBuzz(7));
       assertEquals("7", TestFizzBuzz.fizzBuzzInJava8(7));
       assertEquals("7", TestFizzBuzz.fizzBuzzSolutionJava8(7));
    }

    public static String fizzBuzz(int number) {
        if (number % 15 == 0) {
            return "FizzBuzz";
        } else if (number % 3 == 0) {
            return "Fizz";
        } else if (number % 5 == 0) {
            return "Buzz";
        }
        return Integer.toString(number);
    }

    /**
     * FizzBuzz Solution using Java 8 Optional, map and Stream map() function is * really useful here. * * @param number * @return Fizz, Buzz, FizzBuzz or the number itself
     */
    public static String fizzBuzzInJava8(int number) {
        String result = Optional.of(number)
                .map(n -> (n % 3 == 0 ? "Fizz" : "") + (n % 5 == 0 ? "Buzz" : ""))
                .get();
        return result.isEmpty() ? Integer.toString(number) : result;
    }

    /**
     * Another Java 8 solution, this time its little bit more expressive * for Java 8 newbie.
     */

    public static String fizzBuzzSolutionJava8(int input) {
        return Optional.of(input).map(i -> {
            if (i % (3 * 5) == 0) {
                return "FizzBuzz";
            } else if (i % 3 == 0) {
                return "Fizz";
            } else if (i % 5 == 0) {
                return "Buzz";
            } else {
                return Integer.toString(i);
            }
        }).get();
    }


}
