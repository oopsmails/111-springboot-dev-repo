package com.oopsmails.springboot.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringKafkaDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaDockerApplication.class, args);
	}

}
