package com.oopsmails.exceptionhandling.model;

import lombok.Data;

@Data
public class NestedBean {
    private String value;
    private NestedBeanLayer2 nestedBeanLayer2;
}
