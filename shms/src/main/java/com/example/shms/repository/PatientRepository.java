package com.example.shms.repository;

import com.example.shms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Patient> findByDoctor_DoctorId(Integer doctorId);
    List<Patient> findByDoctorIsNull();
}
