package com.oopsmails.model;

import lombok.Data;

@Data
public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;

    public String calculateFullName(Customer customer) {
        return calculateFullName(customer, null);
    }

    public String calculateFullName(Customer customer, String delimiter) {
        String adding = (delimiter == null || "".equals(delimiter.trim())) ? " " : delimiter;
        if (customer == null) {
            return null;
        }

        return customer.getFirstName() + adding + middleName + adding + customer.getLastName();
    }
}
