package com.oopsmails.springboot.javamain.example;

import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.javamain.model.Employee;
import com.oopsmails.springboot.javamain.repository.EmployeeRepository;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.event.annotation.BeforeTestClass;

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

    @BeforeTestClass
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
//    @Disabled("file path diff between Windows and Linux")
    public void testObjectMapper() throws Exception {
        String fileName = "testData-employeeList.json";
        File jsonFile = new File(getTestFileNameWithPath(fileName));
        InputStream inputStream = new FileInputStream(jsonFile);

//        List<Employee> result = objectMapper.readValue(jsonCarArray, new TypeReference<List<Employee>>(){});

        List<Employee> result = objectMapper.readValue(jsonFile, List.class);
        System.out.println("result = " + result);


        String jsonStr = objectMapper.writeValueAsString(result);
        System.out.println("jsonStr = " + jsonStr);

        String prettyJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("prettyJsonStr = " + prettyJsonStr);
    }
    @Test
    public void myTest() throws Exception {
        String expectedJson = FileUtils.readFileToString(new File(getTestFileNameWithPath("./expected.json")));
        String actualJson = FileUtils.readFileToString(new File(getTestFileNameWithPath("./actual-01.json")));
//        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
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
