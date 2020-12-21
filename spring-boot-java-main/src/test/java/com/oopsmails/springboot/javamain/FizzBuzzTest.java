package com.oopsmails.springboot.javamain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class FizzBuzzTest {

    @Test
    public void testWithNumberIsDividableBy3() {
        Assert.assertEquals("Fizz", FizzBuzzTest.fizzBuzz(3));
        Assert.assertEquals("Fizz", FizzBuzzTest.fizzBuzzInJava8(3));
        Assert.assertEquals("Fizz", FizzBuzzTest.fizzBuzzSolutionJava8(3));
    }

    @Test
    public void testWithNumberIsDividableBy5() {
        Assert.assertEquals("Buzz", FizzBuzzTest.fizzBuzz(5));
        Assert.assertEquals("Buzz", FizzBuzzTest.fizzBuzzInJava8(5));
        Assert.assertEquals("Buzz", FizzBuzzTest.fizzBuzzSolutionJava8(5));
    }

    @Test
    public void testWithNumberIsDividableBy15() {
        Assert.assertEquals("FizzBuzz", FizzBuzzTest.fizzBuzz(15));
        Assert.assertEquals("FizzBuzz", FizzBuzzTest.fizzBuzzInJava8(15));
        Assert.assertEquals("FizzBuzz", FizzBuzzTest.fizzBuzzSolutionJava8(15));
        Assert.assertEquals("FizzBuzz", FizzBuzzTest.fizzBuzz(45));
        Assert.assertEquals("FizzBuzz", FizzBuzzTest.fizzBuzzInJava8(45));
        Assert.assertEquals("FizzBuzz", FizzBuzzTest.fizzBuzzSolutionJava8(45));
    }

    @Test
    public void testOtherNumbers() {
        Assert.assertEquals("1", FizzBuzzTest.fizzBuzz(1));
        Assert.assertEquals("1", FizzBuzzTest.fizzBuzzInJava8(1));
        Assert.assertEquals("1", FizzBuzzTest.fizzBuzzSolutionJava8(1));
        Assert.assertEquals("7", FizzBuzzTest.fizzBuzz(7));
        Assert.assertEquals("7", FizzBuzzTest.fizzBuzzInJava8(7));
        Assert.assertEquals("7", FizzBuzzTest.fizzBuzzSolutionJava8(7));
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
