package com.oopsmails.springboot.mockbackend.employee.controller;

import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee-api")
@Slf4j
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    @GetMapping("")
// @PreAuthorize("#oauth2.hasScope('read')")
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @PostMapping("")
// @PreAuthorize("#oauth2.hasScope('write') and #oauth2.hasScope('read')")
    public Employee add(@RequestBody Employee employee) {
        return repository.add(employee);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        return repository.findByDepartment(departmentId);
    }

    @GetMapping("/organization/{organizationId}")
    public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        return repository.findByOrganization(organizationId);
    }
}

