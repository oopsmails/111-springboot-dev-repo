package com.oopsmails.springboot.javamain.predicate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.javamain.model.Employee;
import com.oopsmails.springboot.javamain.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeePredicateTest extends SpringBootJavaGenericTestBase {
    @Autowired
    private ObjectMapper objectMapper;

    private List<Employee> employeeListTest = new ArrayList<>();

    private List<Employee> employeeList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        String fileName = "testData-employeeList.json";
        employeeListTest = JsonUtils.jsonFileToObject(getTestFileNameWithPath(fileName),
                new TypeReference<List<Employee>>() {
                });

        // This is way more faster
        employeeList = Arrays.asList(objectMapper.readValue(new File(getTestFileNameWithPath(fileName)), Employee[].class));
    }

    @Test
    public void testEmployeePredicateDepName() throws Exception {
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
    }
}
