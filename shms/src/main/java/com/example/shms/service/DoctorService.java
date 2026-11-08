package com.example.shms.service;
import com.example.shms.dto.PatientResponse;
import com.example.shms.entity.Doctor;
import com.example.shms.repository.DoctorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getMyProfile() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return doctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));
    }

    public Doctor updateMyProfile(Doctor updatedDoctor) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        doctor.setName(updatedDoctor.getName());
        doctor.setPhone(updatedDoctor.getPhone());
        doctor.setSpecialization(updatedDoctor.getSpecialization());

        return doctorRepository.save(doctor);
    }
}
