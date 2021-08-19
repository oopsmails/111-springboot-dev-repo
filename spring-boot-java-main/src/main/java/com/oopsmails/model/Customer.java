package com.oopsmails.model;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
}
