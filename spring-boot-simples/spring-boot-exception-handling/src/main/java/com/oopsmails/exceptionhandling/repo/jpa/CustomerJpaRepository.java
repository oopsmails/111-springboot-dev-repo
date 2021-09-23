package com.oopsmails.exceptionhandling.repo.jpa;

import com.oopsmails.exceptionhandling.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {
	
	List<CustomerEntity> findByFirstName(String firstName);
	
}