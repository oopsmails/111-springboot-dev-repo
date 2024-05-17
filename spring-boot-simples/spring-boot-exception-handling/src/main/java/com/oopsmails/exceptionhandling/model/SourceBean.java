package com.oopsmails.exceptionhandling.model;

import lombok.Data;

@Data
public class SourceBean {
    private String name;

    private int age;
    private String email;
    private String sourceName; // for different field name, NOT copied
    private NestedBean nested;

    private NestedBean2 secondNestedBean; // for same field name, BUT different type
}
