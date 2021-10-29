package com.oopsmails.exceptionhandling.domain;

//import com.oopsmails.common.domain.logging.LoggingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private Integer customerId;
	private String firstName;
	private String lastName;
	private Integer age;

//	private LoggingDto loggingDto;
}

