package com.oopsmails.springboot.javamain.predicate;

import com.oopsmails.springboot.javamain.model.Employee;

import java.util.function.Predicate;

public class EmployeePredicate implements Predicate<Employee> {
    public EmployeePredicate(EmployeePredicateParam employeePredicateParam) {

    }

    @Override
    public boolean test(Employee employee) {

        return false;
    }

}
