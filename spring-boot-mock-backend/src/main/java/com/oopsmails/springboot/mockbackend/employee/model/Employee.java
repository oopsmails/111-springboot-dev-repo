package com.oopsmails.springboot.mockbackend.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(of = {"id", "organizationId", "departmentId", "name", "age", "position"}, exclude = {"cryptoSecretProperty", "mandatoryProperty"}) // exclude for showing syntax only
public class Employee {
    public Employee(Long organizationId, Long departmentId, String name, int age, String position) {
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public Employee(Long id, Long organizationId, Long departmentId, String name, int age, String position) {
        this(organizationId, departmentId, name, age, position);
        this.id = id;
    }

    private Long id;
    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;
    private EmployeeTitle employeeTitle;

    private String cryptoSecretProperty;
    private String mandatoryProperty;
}

