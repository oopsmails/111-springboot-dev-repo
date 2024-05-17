package com.oopsmails.exceptionhandling.model;

import lombok.Data;

@Data
public class DestinationBean {
    private String name;

    private int age;
    private String email;
    private String destName;
    private NestedBean nested;

    private NestedBean secondNestedBean;
}
