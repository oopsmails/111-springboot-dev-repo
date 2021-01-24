package com.oopsmails.springangularauth.repositories;

import com.oopsmails.springangularauth.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
	@Override
    void delete(Product deleted);
}