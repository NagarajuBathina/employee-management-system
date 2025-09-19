package com.example.ems.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.ems.entity.Employee;
import com.example.ems.service.EmployeeService;

public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee e) {
        Employee existing = employeeService.getEmployeeById(id);
        existing.setEmail(e.getEmail());
        existing.setPhone(e.getPhone());
        existing.setName(e.getName());
        existing.setSalary(e.getSalary());
        return ResponseEntity.ok(employeeService.savEmployee(e));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Deleted");
    }
}
