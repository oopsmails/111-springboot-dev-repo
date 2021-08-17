package com.oopsmails.springboot.mockbackend.controller;

import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.repository.EmployeeRepository;
import com.oopsmails.springboot.mockbackend.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@Slf4j
public class GenericMockController {
    @Autowired
    EmployeeRepository repository;

    @GetMapping("/ping")
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @PostMapping("/pingpost")
    public String pingPost(@RequestBody String anyThing) throws Exception {
        return JsonUtils.readFileAsString("data/ping/ping.post.response.data.json");
    }
}
