package com.oopsmails.exceptionhandling.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.oopsmails.exceptionhandling.domain.Customer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerGetResponseDto {	
	
	private Integer customerId;
	private String name;
	private Integer age;
	
	public CustomerGetResponseDto(Customer customer) {
		this.customerId = customer.getCustomerId();
		this.name = String.format("%s %s", customer.getFirstName(), customer.getLastName());
		this.age = customer.getAge();
	}

}