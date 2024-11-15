package com.oopsmails.springboot.javamain.predicate;

import com.oopsmails.springboot.javamain.model.Employee;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

@Component
@Slf4j
@Getter
public class EmployeePredicates {
    private final BiPredicate<Employee, Integer> employeePredicateAgeBiggerThan = (e, age) -> e.getAge() > age;
}
