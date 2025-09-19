package com.example.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ems.entity.Employee;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Override
    public Employee savEmployee(Employee e) {
        if (e.getEmail() != null)
            e.setEmail(e.getEmail().toLowerCase());
        return repo.save(e);
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("employee not found" + id));
    }

    @Override
    public void deleteEmployee(long id) {
        repo.deleteById(id);
    }
}
