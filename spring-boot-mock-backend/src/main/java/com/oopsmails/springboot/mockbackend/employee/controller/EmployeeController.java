package com.oopsmails.springboot.mockbackend.employee.controller;

import com.oopsmails.springboot.mockbackend.annotation.crypto.CryptoCheck;
import com.oopsmails.springboot.mockbackend.annotation.crypto.CryptoCheckPayload;
import com.oopsmails.springboot.mockbackend.annotation.crypto.CryptoCheckSignature;
import com.oopsmails.springboot.mockbackend.employee.model.Developer;
import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/backendmock/employee-api")
@Slf4j
public class EmployeeController {

    @Value("#{'${props.set.test}'.split(',')}")
    private Set<String> propsSetTest;

    @Autowired
    EmployeeRepository repository;

    @GetMapping("")
// @PreAuthorize("#oauth2.hasScope('read')")
    public List<Employee> findAll() {
        // Testing extract CSV string into Set<String> from .properties file
        log.info("propsSetTest = {}", propsSetTest);

        // Testing find real subclass type
        Employee dev = new Developer();
        log.info("dev.getClass = {}", dev.getClass());
        log.info("dev.getClass.getName = {}", dev.getClass().getName());
        log.info("dev.getClass.getTypeName = {}", dev.getClass().getTypeName());
        log.info("dev.getClass.getCanonicalName = {}", dev.getClass().getCanonicalName());
        log.info("dev.getClass.getSimpleName = {}", dev.getClass().getSimpleName());

        dev.setId(999L);
        dev.setOrganizationId(1L);
        dev.setDepartmentId(2L);
        dev.setName("TestingName");
        dev.setAge(33);
        dev.setPosition("Tech Lead");
        ((Developer)dev).setJavaSkillLevel(5);
        log.info("Testing toString(), dev = {}", dev);

        List<Employee> result = repository.findAll();
        log.info("Testing toString(), employee = {}", result.get(0));

        return result;
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

