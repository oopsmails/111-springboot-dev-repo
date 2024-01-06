package com.oopsmails.springboot.engine;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = { //
        EngineServiceTest.class, //
        EngineServiceTest.EngineServiceTestConfig.class, //
})
@Slf4j
public class EngineServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EngineService engineService;

    @Test
    public void testEngineService() throws Exception {
        log.info("testEngineService .....................");

        assertNotNull(applicationContext);
    }

    @TestConfiguration
    @ComponentScan({
            "com.oopsmails.springboot.javamain",
            "com.oopsmails.springboot.async",
            "com.oopsmails.springboot.service"
    })
    public static class EngineServiceTestConfig {

        @Bean
        public EngineService engineService() {
            EngineService result = new EngineService();

            return result;
        }
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2019, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
