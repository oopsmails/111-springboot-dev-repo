package com.oopsmails.generaljava.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStreamMain {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        // Using filter to create a new stream with even numbers
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Filtered numbers using filter: " + evenNumbers);

        // Using removeIf to remove even numbers from the original list
        numbers.removeIf(n -> n % 2 == 0);

        System.out.println("Numbers after using removeIf: " + numbers);
    }
}
