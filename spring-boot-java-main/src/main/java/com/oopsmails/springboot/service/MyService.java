package com.oopsmails.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(com.oopsmails.springboot.service.ServiceProperties.class)
@Slf4j
public class MyService {

	private final com.oopsmails.springboot.service.ServiceProperties serviceProperties;

	public MyService(com.oopsmails.springboot.service.ServiceProperties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	public String message() {
		log.info("this.serviceProperties.getMessage(): [{}]", this.serviceProperties.getMessage());
		return this.serviceProperties.getMessage();
	}
}
