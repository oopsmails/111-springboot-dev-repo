package com.oopsmails.springboot.mockbackend.employee.controller;

import com.oopsmails.springboot.mockbackend.employee.annotation.valuecheck.CryptoCheck;
import com.oopsmails.springboot.mockbackend.employee.annotation.valuecheck.CryptoCheckPayload;
import com.oopsmails.springboot.mockbackend.employee.annotation.valuecheck.CryptoCheckSignature;
import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @CryptoCheck(cryptoSecretProperty = "employee.create.secret",
                    mandatoryProperty = "employee.create.secret.mandatory")
    public Employee add(
            @RequestHeader(value = "crypto-payload", required = false)
            @CryptoCheckPayload
            String cryptoCheckPayload,
            @RequestHeader(value = "crypto-signature", required = false)
            @CryptoCheckSignature
            String cryptoCheckSignature,
            @RequestBody Employee employee
    ) {
        log.info("cryptoCheckPayload: [{}]", cryptoCheckPayload);
        log.info("cryptoCheckSignature: [{}]", cryptoCheckSignature);
        log.info("employee: [{}]", employee);
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

