package com.oopsmails.springboot.oauth2.fa2.dto;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
}