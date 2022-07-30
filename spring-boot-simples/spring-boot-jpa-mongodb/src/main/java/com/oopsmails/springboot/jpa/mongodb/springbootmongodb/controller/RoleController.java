package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.controller;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.Role;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/roles")
    public List<Role> roles() {
        List<Role> result = roleRepository.findAll();
        result.forEach(System.out::println);
        return result;
    }

    @PostMapping("/role")
    public ResponseEntity<?> create(@RequestBody Role role) {
        try {
            roleRepository.save(role);
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}