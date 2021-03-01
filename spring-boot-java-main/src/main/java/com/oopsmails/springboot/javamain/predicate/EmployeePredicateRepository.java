package com.oopsmails.springboot.javamain.predicate;

import com.oopsmails.springboot.javamain.model.Employee;
import lombok.Data;

import java.util.function.Predicate;

public class EmployeePredicateRepository {
    public static Predicate<Employee> employeePredicateDepName = e -> e.getDepartmentId() == 1 && e.getName().startsWith("A");
    public static Predicate<Employee> employeePredicateAge = e -> e.getAge() > 30;

    public static class EmployeePredicate implements Predicate<Employee> {
        public EmployeePredicate(EmployeePredicateParam employeePredicateParam) {

        }
        @Override
        public boolean test(Employee employee) {

            return false;
        }
    }

    @Data
    public static class EmployeePredicateParam {
        private int ageCriteria;
        private String nameStartWithCriteria;
    }
}
