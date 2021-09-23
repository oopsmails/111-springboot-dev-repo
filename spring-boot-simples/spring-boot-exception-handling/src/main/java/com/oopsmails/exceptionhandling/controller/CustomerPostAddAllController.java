package com.oopsmails.exceptionhandling.controller;

import com.oopsmails.exceptionhandling.constraint.CustomMaxSizeConstraint;
import com.oopsmails.exceptionhandling.domain.Customer;
import com.oopsmails.exceptionhandling.json.CustomerGetResponseDto;
import com.oopsmails.exceptionhandling.json.CustomerPostRequestDto;
import com.oopsmails.exceptionhandling.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class CustomerPostAddAllController {
	
	@Autowired
	private CustomerService customerService;
	
	// Sample Input (Valid: 200-Ok): [  {"firstName": "Purnima",     "lastName": "Jain",     "age": 93 }, {"firstName": "Purnima_2",     "lastName": "Jain_2",     "age": 43 }   ]
	// Sample Input (Valid: 400-Bad Request): [  ]
	// Throws ConstraintViolationException with message "List of Customers to add cannot be empty" when an empty arraylist is passed as JSON
	// Sample Input (Valid: 400-Bad Request): [  {"lastName": "Jain",     "age": 93 }, {"firstName": "Purnima_2",     "lastName": "Jain_2",     "age": 43 }   ]
	// Throws ConstraintViolationException with messages "Customer First Name cannot be null" & "Customer First Name cannot be blank"	
	// Sample Input (Valid: 400-Bad Request): [
	// 			{"firstName": "Purnima",       "lastName": "Jain",       "age": 93 },
	// 			{"firstName": "Purnima_2",     "lastName": "Jain_2",     "age": 43 }, 
	// 			{"firstName": "Jane",          "lastName": "Doe",        "age": 23 }, 
	// 			{"firstName": "John",          "lastName": "Doe",        "age": 33 }
	// ]
	// Throws ConstraintViolationException with message "The input list cannot contain more than 3 customers."
	@PostMapping(value = "/customers/addAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerGetResponseDto>> addAllCustomers(@RequestBody @NotEmpty(message = "List of Customers to add cannot be empty") @CustomMaxSizeConstraint List<@Valid CustomerPostRequestDto> customerPostRequestDtoList) {
		List<Customer> customerList = customerPostRequestDtoList.stream().map(customerPostRequestDto -> customerPostRequestDto.toCustomer()).collect(Collectors.toList());
		
		List<Customer> savedCustomerList = customerList.stream().map(customer -> customerService.saveCustomer(customer)).collect(Collectors.toList());
		
		List<CustomerGetResponseDto> customerGetResponseDtoList = savedCustomerList.stream().map(customer -> new CustomerGetResponseDto(customer)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(customerGetResponseDtoList);
	}

}
