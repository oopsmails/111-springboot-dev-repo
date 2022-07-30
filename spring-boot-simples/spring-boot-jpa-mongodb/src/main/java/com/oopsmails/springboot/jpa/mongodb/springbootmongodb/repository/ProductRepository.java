package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Override
    void delete(Product deleted);
}