package com.oopsmails.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("service.message=Hello")
public class MyServiceTestServicePropertiesTest {

    @Autowired
    private MyServiceTestServiceProperties myServiceTestServiceProperties;

    @Test
    public void contextLoads() {
//		assertThat(myService.message()).isNotNull();
    }

    @Test
    public void serviceMessageNotNull() {
        String msg = myServiceTestServiceProperties.message();
        assertThat(msg).isNotNull();
        assertThat(msg).isEqualTo("Hello");
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}
