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
@RequestMapping("/api/doctors")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // 1. Get all doctors
    @GetMapping
    public ResponseEntity<List<Employee>> getAllDoctors() {
        List<Employee> doctors = employeeRepository.findAll();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // 2. Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getDoctorById(@PathVariable("id") Long id) {
        Optional<Employee> doctor = employeeRepository.findById(id);
        if (doctor.isPresent()) {
            return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. Get doctors by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> getDoctorsByStatus(@PathVariable("status") String status) {
        List<Employee> doctors = employeeRepository.findByStatus(status);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // 4. Get doctors by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getDoctorsByDepartment(@PathVariable("department") String department) {
        List<Employee> doctors = employeeRepository.findByDepartment(department);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}