package com.oopsmails.exceptionhandling.constraint;

import com.oopsmails.exceptionhandling.json.CustomerPostRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CustomMaxSizeConstraintValidator implements ConstraintValidator<CustomMaxSizeConstraint, List<CustomerPostRequestDto>> {

	@Override
	public boolean isValid(List<CustomerPostRequestDto> values, ConstraintValidatorContext context) {
		return values.size() <= 3;
	}

}