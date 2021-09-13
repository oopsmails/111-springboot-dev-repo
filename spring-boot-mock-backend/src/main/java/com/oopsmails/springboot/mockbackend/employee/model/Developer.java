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
@ToString(of = {"organizationId", "departmentId", "name", "age", "position", "javaSkillLevel"}, exclude = {"id"})
public class Developer extends Employee {
    private int javaSkillLevel;
}
