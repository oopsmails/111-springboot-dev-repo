package com.oopsmails.springboot.mockbackend.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.oopsmails.springboot.mockbackend.SpringBootBackendMockApplication;
import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@WebMvcTest
//@WebMvcTest(controllers = EmployeeController.class)
@SpringBootTest(classes = {
        SpringBootBackendMockApplication.class,
        EmployeeControllerTest.EmployeeControllerTestConfig.class
})
public class EmployeeControllerTest {

//    @Autowired
//    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

//    @BeforeTestClass
//    public void setup() throws Exception {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//    }

    @Test
// @WithMockUser(username = "admin1", roles = "ADMIN")
    public void getAllEmployeesAPI() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/employee-api")
// .with(user("admin1").roles("ADMIN"))
// .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
// .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<Employee> employeeList = objectMapper.readValue(content, typeFactory.constructCollectionType(List.class, Employee.class));

        assertTrue(employeeList.size() > 0);
    }

    @TestConfiguration
    public static class EmployeeControllerTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2019, Month.FEBRUARY, 20, 10, 11, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}

