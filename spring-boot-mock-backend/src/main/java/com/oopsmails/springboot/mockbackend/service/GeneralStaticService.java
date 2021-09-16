package com.oopsmails.springboot.mockbackend.service;

import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.model.EmployeeTitle;

import java.util.ArrayList;
import java.util.List;

public class GeneralStaticService {
    public static List<Employee> employees = new ArrayList<>();
//    final Map<Class, Function<T, R>> tempFunctionMap = new HashMap<>();

    static {
        employees.add(new Employee(0L, 1L, 1L, "John Smith", 34, "Analyst"));
        employees.add(new Employee(1L, 1L, 1L, "Darren Hamilton", 37, "Manager"));
        employees.add(new Employee(2L, 1L, 1L, "Tom Scott", 26, "Developer"));
        employees.add(new Employee(3L, 1L, 2L, "Anna London", 39, "BA"));
        employees.add(new Employee(4L, 1L, 2L, "Patrick Dempsey", 27, "Developer"));
        employees.add(new Employee(5L, 2L, 3L, "Kevin Price", 38, "QA"));
        employees.add(new Employee(6L, 2L, 3L, "Ian Scott", 34, "Developer"));
        employees.add(new Employee(7L, 2L, 3L, "Andrew Campton", 30, "Manager"));
        employees.add(new Employee(8L, 2L, 4L, "Steve Franklin", 25, "Developer"));
        employees.add(new Employee(9L, 2L, 4L, "Elisabeth Smith", 30, "Developer"));
    }

    public static Employee getEmployeeByIdMock(Long id) {
        try {
            Thread.sleep(1000);
            return employees.get(id.intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Employee> getEmployeeByIdsMock(List<Long> ids) {
        List<Employee> result = new ArrayList<>();
        ids.forEach(id -> result.add(getEmployeeByIdMock(id)));
        return result;
    }

    public static EmployeeTitle getEmployeeTitleByPosition(String position) {
        try {
            Thread.sleep(1000);
            return EmployeeTitle.fromValue(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Double getDoubleWithDelay(Double iDouble) {
        try {
            Thread.sleep(1000);
            return iDouble;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
