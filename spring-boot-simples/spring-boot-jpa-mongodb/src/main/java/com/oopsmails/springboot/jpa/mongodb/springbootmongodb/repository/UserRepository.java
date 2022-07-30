package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}