package com.oopsmails.model;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;

    private String fullName;

    public String calculateFullName(CustomerDto customerDto) {
        return calculateFullName(customerDto, null);
    }

    public String calculateFullName(CustomerDto customerDto, String delimiter) {
        String adding = (delimiter == null || "".equals(delimiter.trim())) ? " " : delimiter;
        if (customerDto == null || customerDto.getMiddleName() == null) {
            return firstName + adding + lastName;
        }

        return firstName + adding + customerDto.getMiddleName() + adding + lastName;
    }
}
