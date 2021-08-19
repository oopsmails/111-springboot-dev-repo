package com.oopsmails.springboot.javamain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsmails.springboot.javamain.utils.JsonUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = { //
        SpringBootJavaMainApplication.class, //
        SpringBootJavaGenericTestBase.SpringBootJavaGenericTestBaseTestConfig.class, //
})
public class SpringBootJavaGenericTestBase {
    @Autowired
    protected ObjectMapper objectMapper;

    private static String TEST_DATA_FILE_FOLDER = JsonUtils.PROJECT_PATH + "/src/test/resources/testdata";

    @TestConfiguration
    @ComponentScan({
            "com.oopsmails.springboot.javamain",
            "com.oopsmails.springboot.async",
            "com.oopsmails.springboot.service"
    })
    public static class SpringBootJavaGenericTestBaseTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2019, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }

    protected String getTestFileNameWithPath(String fileName) {
        return TEST_DATA_FILE_FOLDER + "/" + fileName;
    }
}
