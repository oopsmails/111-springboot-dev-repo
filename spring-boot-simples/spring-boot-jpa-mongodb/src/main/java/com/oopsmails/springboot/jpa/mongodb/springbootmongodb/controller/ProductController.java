package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.controller;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.Product;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.Role;
import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository.ProductRepository;
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
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/products")
    public List<Product> products() {
        List<Product> result = productRepository.findAll();
        result.forEach(System.out::println);
        return result;
    }

    @PostMapping("/product")
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            productRepository.save(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}