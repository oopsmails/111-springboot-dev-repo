package com.oopsmails.springboot.javamain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = { //
        SpringBootJavaMainApplication.class, //
        SpringBootJavaGenericTestBase.SpringBootJavaGenericTestBaseTestConfig.class, //
})
public class SpringBootJavaGenericTestBase {
    @Autowired
    protected ObjectMapper objectMapper;

    @TestConfiguration
    @ComponentScan("com.oopsmails.springboot2.app.all.javageneric")
    public static class SpringBootJavaGenericTestBaseTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2019, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
