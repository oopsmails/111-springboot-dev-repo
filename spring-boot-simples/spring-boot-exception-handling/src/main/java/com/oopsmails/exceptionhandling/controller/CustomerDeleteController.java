package com.oopsmails.exceptionhandling.controller;

import com.oopsmails.exceptionhandling.domain.Customer;
import com.oopsmails.exceptionhandling.exception.custom.ResourceNotFoundException;
import com.oopsmails.exceptionhandling.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class CustomerDeleteController {
	
	@Autowired
	private CustomerService customerService;

	// Sample Input (Valid: 204-No Content): http://localhost:8080/customers/1408
	// Sample Input (Valid: 404-Not Found   with the message "Customer was not found for parameters {customerId=1409}"): http://localhost:8080/customers/1409
	@DeleteMapping(value = "/customers/{customerId}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable("customerId") Integer customerId) {
		log.info("Input Customer Id :: {}", customerId);
		
		Optional<Customer> deletedCustomerOptional = customerService.deleteCustomerById(customerId);		
		
		@SuppressWarnings("unused")
		Customer customer = deletedCustomerOptional.orElseThrow(() -> new ResourceNotFoundException(Customer.class, "customerId", customerId.toString()));
		// return ResponseEntity.ok().body("User deleted with success!");
		return ResponseEntity.noContent().build();
	}

}
