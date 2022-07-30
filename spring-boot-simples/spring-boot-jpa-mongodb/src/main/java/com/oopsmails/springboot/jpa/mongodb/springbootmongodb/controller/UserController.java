package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.controller;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.User;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository.UserRepository;
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
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> products() {
        List<User> result = userRepository.findAll();
        result.forEach(System.out::println);
        return result;
    }

    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}