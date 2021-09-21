package com.oopsmails.openapi.json.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.oopsmails.openapi.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@JsonRootName("customer")
@Schema(name = "CustomerPostRequestDto", description = "Customer POST Request Model")
public class CustomerPostRequestDto {
	
	@Schema(description = "First Name of the Customer", example = "John", required = true)
	private String firstName;

	@Schema(description = "Last Name of the Customer", example = "Doe", required = true)
	private String lastName;

	@Schema(description = "Gender of the Customer", example = "MALE", required = true)
	private GenderEnum gender;

	@Valid
	private List<HobbyPostRequestDto> hobbies;

}
