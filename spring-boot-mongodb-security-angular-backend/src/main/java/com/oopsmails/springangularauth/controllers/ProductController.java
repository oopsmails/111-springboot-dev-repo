package com.oopsmails.springangularauth.controllers;

import com.oopsmails.springangularauth.models.Product;
import com.oopsmails.springangularauth.repositories.ProductCrudRepository;
import com.oopsmails.springangularauth.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class ProductController {

	@Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCrudRepository productCrudRepository;

//    @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/products")
    public Iterable<Product> product() {
//        return productRepository.findAll();
        List<Product> result = productCrudRepository.findAll().collectList().block();
        result.forEach(System.out::println);
        return result;
    }
}