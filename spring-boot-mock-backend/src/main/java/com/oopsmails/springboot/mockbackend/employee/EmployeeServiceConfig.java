package com.oopsmails.springboot.mockbackend.employee;

import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
@Slf4j
public class EmployeeServiceConfig {

    @Bean
    EmployeeRepository repository() {
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

        Random rand = new Random();
        List<String> positionList = Arrays.asList("Analyst", "Manager", "Developer", "QE");

        for (int i = 0; i < 100; i++) {
            long orgId = i % 2 + 1;
            long depId = i % 4 + 1;
            String name = "First" + i + " Last" + i;
            int age = rand.nextInt(45) + 21;
            int randomIndex= i % 4;
//            int randomIndex = rand.nextInt(positionList.size());
            String position = positionList.get(randomIndex);
            Employee employee = new Employee(orgId, depId, name, age, position);
            repository.add(employee);
            log.debug("Adding Employee = [{}]", employee);
        }
        return repository;
    }
}
