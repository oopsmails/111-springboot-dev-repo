package com.oopsmails.springboot.oauth2.sociallogin.dto;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
}