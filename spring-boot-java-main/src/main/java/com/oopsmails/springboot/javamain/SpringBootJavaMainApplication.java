package com.oopsmails.springboot.javamain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oopsmails.springboot.javamain.model.Employee;
import com.oopsmails.springboot.javamain.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.time.Clock;
import java.time.ZoneId;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class SpringBootJavaMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJavaMainApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule dateSerializerModule = new SimpleModule();

        ObjectMapper result = new ObjectMapper();
        result.registerModule(dateSerializerModule);
        result.registerModule(new JavaTimeModule());
        result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        result.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        result.configure(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL, true);

        return result;
    }

    @Bean
    public Clock appClock() {
        Clock result1 = Clock.system(ZoneId.of("Canada/Eastern"));
        Clock result = Clock.systemDefaultZone();
        return result;
    }

    @Bean
    EmployeeRepository employeeRepository() {
        EmployeeRepository repository = new EmployeeRepository();
        repository.add(new Employee(1L, 1L, "John Smith", 34, "Analyst"));
        repository.add(new Employee(1L, 1L, "Darren Hamilton", 37, "Manager"));
        repository.add(new Employee(1L, 1L, "Tom Scott", 26, "Developer"));
        repository.add(new Employee(1L, 2L, "Anna London", 39, "Analyst"));
        repository.add(new Employee(1L, 2L, "Patrick Dempsey", 27, "Developer"));
        repository.add(new Employee(2L, 3L, "Kevin Price", 38, "Developer"));
        repository.add(new Employee(2L, 3L, "Ian Scott", 34, "Developer"));
        repository.add(new Employee(2L, 3L, "Andrew Campton", 30, "Manager"));
        repository.add(new Employee(2L, 4L, "Steve Franklin", 25, "Developer"));
        repository.add(new Employee(2L, 4L, "Elisabeth Smith", 30, "Developer"));
        return repository;
    }
}
