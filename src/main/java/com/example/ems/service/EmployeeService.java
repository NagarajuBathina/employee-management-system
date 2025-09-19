package com.example.ems.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.ems.entity.Employee;

public interface EmployeeService {

    Employee savEmployee(Employee e);

    Page<Employee> getAllEmployees(Pageable pageable);

    Employee getEmployeeById(long id);

    void deleteEmployee(long id);
}