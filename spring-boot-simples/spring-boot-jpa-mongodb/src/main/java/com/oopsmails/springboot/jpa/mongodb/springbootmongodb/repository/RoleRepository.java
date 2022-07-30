package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.repository;

import com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}