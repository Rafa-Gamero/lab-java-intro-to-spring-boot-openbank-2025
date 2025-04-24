package com.labspring.labspring.repository;

import com.labspring.labspring.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT p FROM Patient p WHERE p.admittedBy.department = :department")
    List<Patient> findByAdmittedByDepartment(@Param("department") String department);

    @Query("SELECT p FROM Patient p WHERE p.admittedBy.status = :status")
    List<Patient> findByAdmittedByStatus(@Param("status") String status);
}
