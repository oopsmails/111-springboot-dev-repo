package com.oopsmails.springboot.oauth2.sociallogin.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
	private String accessToken;
	private UserInfo user;
}