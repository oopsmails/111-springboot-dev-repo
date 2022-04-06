package com.oopsmails.springboot.javamain.predicate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.javamain.model.Employee;
import com.oopsmails.springboot.javamain.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeePredicateTest extends SpringBootJavaGenericTestBase {
    private static String fileName = "testData-employeeList.json";

    @Autowired
    private ObjectMapper objectMapper;

//    private List<Employee> employeeListTest = new ArrayList<>();
//
//    private List<Employee> employeeList = new ArrayList<>();


//    @BeforeTestClass
//    public void setUp() throws Exception {
//        String fileName = "testData-employeeList.json";
//        employeeListTest = JsonUtils.jsonFileToObject(getTestFileNameWithPath(fileName),
//                new TypeReference<List<Employee>>() {
//                });
//
//        // This is way more faster
//        employeeList = Arrays.asList(objectMapper.readValue(new File(getTestFileNameWithPath(fileName)), Employee[].class));
//    }

    @Test
    public void testEmployeePredicate() throws Exception {
        List<Employee> employeeList = Arrays.asList(objectMapper.readValue(new File(getTestFileNameWithPath(fileName)), Employee[].class));
        //using allMatch
        boolean b1 = employeeList.stream().allMatch(EmployeePredicateRepository.employeePredicateDepName);
        System.out.println(b1);
        boolean b2 = employeeList.stream().allMatch(EmployeePredicateRepository.employeePredicateAge);
        System.out.println(b2);
        //using anyMatch
        boolean b3 = employeeList.stream().anyMatch(EmployeePredicateRepository.employeePredicateDepName);
        System.out.println(b3);
        boolean b4 = employeeList.stream().anyMatch(EmployeePredicateRepository.employeePredicateAge);
        System.out.println(b4);
        //using noneMatch
        boolean b5 = employeeList.stream().noneMatch(EmployeePredicateRepository.employeePredicateDepName);
        System.out.println(b5);
        assertTrue(b5, "Should at least match.");
    }

    @Test
    public void testEmployeePredicateParam() throws Exception {
        List<Employee> employeeList = Arrays.asList(objectMapper.readValue(new File(getTestFileNameWithPath(fileName)), Employee[].class));
        EmployeePredicateRepository.EmployeePredicateParam employeePredicateParam = new EmployeePredicateRepository.EmployeePredicateParam();
        employeePredicateParam.setNameStartWithCriteria("Tom");

        EmployeePredicateRepository.EmployeePredicate employeePredicate = new EmployeePredicateRepository.EmployeePredicate(employeePredicateParam) {
            @Override
            public boolean test(Employee employee) {
                return employee.getName().contains(employeePredicateParam.getNameStartWithCriteria());
            }
        };
        boolean b1 = employeeList.stream().anyMatch(employeePredicate);
        assertTrue(b1, "Should at least match.");
    }
}
