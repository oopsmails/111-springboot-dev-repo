package com.oopsmails.springboot.sonarqube.exception;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@Profile("test")
@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {
}
