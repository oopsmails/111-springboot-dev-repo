package com.oopsmails.springangularauth.repositories;

import com.oopsmails.springangularauth.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
}