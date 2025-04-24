package com.labspring.labspring.controller;

import com.labspring.labspring.models.Patient;
import com.labspring.labspring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Helper method to convert Patient to Map (to avoid JSON serialization issues)
    private Map<String, Object> convertPatientToMap(Patient patient) {
        Map<String, Object> patientMap = new HashMap<>();
        patientMap.put("patientId", patient.getPatientId());
        patientMap.put("name", patient.getName());
        patientMap.put("dateOfBirth", patient.getDateOfBirth());

        if (patient.getAdmittedBy() != null) {
            Map<String, Object> doctorMap = new HashMap<>();
            doctorMap.put("employeeId", patient.getAdmittedBy().getEmployeeId());
            doctorMap.put("name", patient.getAdmittedBy().getName());
            doctorMap.put("department", patient.getAdmittedBy().getDepartment());
            doctorMap.put("status", patient.getAdmittedBy().getStatus());
            patientMap.put("admittedBy", doctorMap);
        } else {
            patientMap.put("admittedBy", null);
        }

        return patientMap;
    }

    // 5. Get all patients
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<Map<String, Object>> result = patients.stream()
                .map(this::convertPatientToMap)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 6. Get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPatientById(@PathVariable("id") Long id) {
        Optional<Patient> patientOpt = patientRepository.findById(id);
        if (patientOpt.isPresent()) {
            Map<String, Object> result = convertPatientToMap(patientOpt.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 7. Get patients by date of birth range
    @GetMapping("/date-range")
    public ResponseEntity<List<Map<String, Object>>> getPatientsByDateOfBirthRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Patient> patients = patientRepository.findByDateOfBirthBetween(startDate, endDate);
        List<Map<String, Object>> result = patients.stream()
                .map(this::convertPatientToMap)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 8. Get patients by admitting doctor's department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Map<String, Object>>> getPatientsByDoctorDepartment(@PathVariable("department") String department) {
        List<Patient> patients = patientRepository.findByAdmittedByDepartment(department);
        List<Map<String, Object>> result = patients.stream()
                .map(this::convertPatientToMap)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 9. Get all patients with a doctor whose status is OFF
    @GetMapping("/doctor-off")
    public ResponseEntity<List<Map<String, Object>>> getPatientsWithDoctorOff() {
        List<Patient> patients = patientRepository.findByAdmittedByStatus("OFF");
        List<Map<String, Object>> result = patients.stream()
                .map(this::convertPatientToMap)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
