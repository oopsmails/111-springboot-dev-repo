package com.oopsmails.springboot.mockbackend.employee.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeTitle {
    DEV("Developer"),
    BA("BA"),
    QA("QA"),
    MANAGER("Manager"),
    ANALYST("Analyst"),
    UNDEFINED("Undefined");

    private final static Map<String, EmployeeTitle> CONSTANTS = new HashMap<>();

    static {
        for (EmployeeTitle c : values()) {
            CONSTANTS.put(c.title, c);
        }
    }

    @Getter
    private final String title;

    EmployeeTitle(final String title) {
        this.title = title;
    }

    @JsonCreator
    public static EmployeeTitle fromValue(String value) {
        EmployeeTitle constant = CONSTANTS.get(value);
        if (constant == null) {
            return EmployeeTitle.UNDEFINED;
        } else {
            return constant;
        }
    }
}
