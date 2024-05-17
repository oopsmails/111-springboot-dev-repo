package com.oopsmails.exceptionhandling.model;

import lombok.Data;

@Data
public class NestedBean2 {
    private String value;
    private NestedBeanLayer2 nestedBeanLayer2;
}
