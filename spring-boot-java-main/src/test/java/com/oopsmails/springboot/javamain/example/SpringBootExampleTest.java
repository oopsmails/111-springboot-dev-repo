package com.oopsmails.springboot.javamain.example;

import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.javamain.model.Employee;
import com.oopsmails.springboot.javamain.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.util.JsonExpectationsHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { //
        SpringBootExampleTest.TempSpringBootTestConfig.class, //
})
//@Slf4j
public class SpringBootExampleTest extends SpringBootJavaGenericTestBase {

    private static Employee mockEmployee = new Employee(1L, 1L, "Oops Mails", 34, "Developer");
    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private Clock appClock;

    @BeforeTestClass
    public void setUp() throws Exception {
//        Employee mockEmployee = new Employee(1L, 1L, "Oops Mails", 34, "Developer");
//        when(employeeRepository.findById(any())).thenReturn(mockEmployee);
//        employeeRepository = mock(EmployeeRepository.class);
    }

    @Test
    public void testBeanOverriding() throws Exception {
        LocalDate localDate = LocalDate.now(appClock);
        System.out.println("localDate = " + localDate);
//        log.info("localDate = {}", localDate);
    }

    @Test
    public void testMockBeanCalling() throws Exception {

        when(employeeRepository.findById(any())).thenAnswer(new Answer<Employee>() {
            private boolean firstCall = true;

            @Override
            public Employee answer(InvocationOnMock invocation) throws Throwable {
                if (firstCall) {
                    firstCall = false;
                    //                    return mock(Employee.class); // Mocked list for the first call
                    return mockEmployee;
                } else {
                    return null; // Null for the second call
                }
            }
        });

        Employee firstCallResult = this.employeeRepository.findById(1L);
        System.out.println("firstCallResult = " + firstCallResult);
        Employee secondCallResult = this.employeeRepository.findById(2L);
        System.out.println("secondCallResult = " + secondCallResult);

        doAnswer(invocation -> {
            Long id = (Long) invocation.getArgument(0);
            if ( id >= 50L) {
                Employee employee = new Employee(id, id, "Oops Mails3", 34, "Developer 3");
                return employee;
            }

            return mockEmployee;
        }).when(employeeRepository).findById(anyLong());

        Employee e3 = this.employeeRepository.findById(52L);
        System.out.println("e3 = " + e3);

        Employee e4 = this.employeeRepository.findById(2L);
        System.out.println("e4 = " + e4);
    }

    @Test
    public void testMockBeanDoAnswer() throws Exception {

        doAnswer(invocation -> {
            Long id = (Long) invocation.getArgument(0);
            if ( id >= 50L) {
                Employee employee = new Employee(id, id, "Oops Mails3", 34, "Developer 3");
                return employee;
            }

            return mockEmployee;
        }).when(employeeRepository).findById(anyLong());

        Employee e3 = this.employeeRepository.findById(52L);
        System.out.println("e3 = " + e3);

        Employee e4 = this.employeeRepository.findById(2L);
        System.out.println("e4 = " + e4);
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
    public void testJsonDiff1() throws Exception {
//        String expectedJson = FileUtils.readFileToString(new File(getTestFileNameWithPath("./expected.json")));
//        String actualJson = FileUtils.readFileToString(new File(getTestFileNameWithPath("./actual-01.json")));
//        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);

        String expectedJson = IOUtils.toString(new File(getTestFileNameWithPath("expected.json")).toURI(), Charset.defaultCharset());
        String actualJson = IOUtils.toString(new File(getTestFileNameWithPath("actual-01.json")).toURI(), Charset.defaultCharset());
//        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }
    @Test
    void testJsonDiff2() throws Exception {
        final String json1 =
                "{" +
                        "  'someObject': {" +
                        "    'someArray': [" +
                        "      {" +
                        "        'someInternalObject': {" +
                        "          'value': '123'" +
                        "        }" +
                        "      }," +
                        "      {" +
                        "        'someInternalObject2': {" +
                        "          'value': '456'" +
                        "        }" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "}";

        final String json2 =
                "{" +
                        "  'someObject': {" +
                        "    'someArray': [" +
                        "      {" +
                        "        'someInternalObject': {" +
                        "          'value': '123'" +
                        "        }" +
                        "      }," +
                        "      {" +
                        "        'someInternalObject2': {" +
                        "          'value': '4567'" +
                        "        }" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "}";

//        new JsonExpectationsHelper().assertJsonEqual(json1, json2, true);
        new JsonExpectationsHelper().assertJsonNotEqual(json1, json2, true);
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
