package com.oopsmails.springboot.javamain.example;

import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.javamain.model.Employee;
import com.oopsmails.springboot.javamain.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { //
        SpringBootExampleTest.TempSpringBootTestConfig.class, //
})
public class SpringBootExampleTest extends SpringBootJavaGenericTestBase {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private Clock appClock;

    @Before
    public void setUp() throws Exception {
        Employee mockEmployee = new Employee(1L, 1L, "Oops Mails", 34, "Developer");
        when(employeeRepository.findById(any())).thenReturn(mockEmployee);
    }

    @Test
    public void testBeanOverriding() throws Exception {
        LocalDate localDate = LocalDate.now(appClock);
        System.out.println("localDate = " + localDate);
    }

    @Test
    public void testObjectMapper() throws Exception {
        String fileName = "testData.json";
        File jsonFile = new File(getFileNameWithPath(fileName));
        InputStream inputStream = new FileInputStream(jsonFile);

//        List<Employee> result = objectMapper.readValue(jsonCarArray, new TypeReference<List<Employee>>(){});

        List<Employee> result = objectMapper.readValue(jsonFile, List.class);
        System.out.println("result = " + result);


        String jsonStr = objectMapper.writeValueAsString(result);
        System.out.println("jsonStr = " + jsonStr);

        String prettyJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("prettyJsonStr = " + prettyJsonStr);


    }

    private String getFileNameWithPath(String fileName) {
        String folder = "/home/albert/Documents/github/springboot2-app-all/spring-boot-app-all-java-generic/src/test/resources/testdata";
        return folder + "/" + fileName;
    }

    @TestConfiguration
    public static class TempSpringBootTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2018, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
