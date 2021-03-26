package com.oopsmails.springangularauth;

import com.oopsmails.springangularauth.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


//@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = { //
        SpringAngularAuthApplication.class, //
        SpringAngularAuthApplicationTests.SpringAngularAuthApplicationTestConfig.class, //
})
public class SpringAngularAuthApplicationTests {
    @Autowired
    protected ObjectMapper objectMapper;



//    @Disabled("Need to run Mongodb")
//    @Test
//    public void contextLoads() throws Exception {
//        Product product = new Product();
//        product.setProdDesc("Toy abc");
//        product.setProdName("Lego Ninja");
//        product.setProdPrice(56.66);
//
//        String prettyJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(product);
//        System.out.println("prettyJsonStr = " + prettyJsonStr);
//    }

    @TestConfiguration
//    @ComponentScan("com.oopsmails.springangularauth")
    public static class SpringAngularAuthApplicationTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2019, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
