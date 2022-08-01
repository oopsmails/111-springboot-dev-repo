package com.oopsmails.generaljava;

import java.util.Arrays;
import java.util.List;

public class SimpleMain {
    public static void main(String[] args) {
        List<String> positionList = Arrays.asList("Analyst", "Manager", "Developer", "QE");

        for (int i = 0; i < 100; i++) {
            long orgId = i % 2 + 1;
            long depId = i % 4 + 1;
            int randomIndex= i % 4;
            System.out.printf("orgId = %d\n", orgId);
            System.out.printf("depId = %d\n", depId);
            System.out.printf("randomIndex = %s, position = %s\n", randomIndex, positionList.get(randomIndex));

        }
    }
}
