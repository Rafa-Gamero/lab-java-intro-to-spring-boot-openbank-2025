package com.labspring.labspring.controller;



import com.labspring.labspring.models.Employee;
import com.labspring.labspring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Employee> getByStatus(@PathVariable Employee.Status status) {
        return repository.findByStatus(status);
    }

    @GetMapping("/department/{dept}")
    public List<Employee> getByDepartment(@PathVariable String dept) {
        return repository.findByDepartment(dept);
    }
}