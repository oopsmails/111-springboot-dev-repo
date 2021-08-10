package com.oopsmails.springboot.mockbackend.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    public Employee() {

    }

    public Employee(Long organizationId, Long departmentId, String name, int age, String position) {
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.name = name;
        this.age = age;
        this.position = position;
    }

    private Long id;
    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;

    private String cryptoSecretProperty;
    private String mandatoryProperty;


}

