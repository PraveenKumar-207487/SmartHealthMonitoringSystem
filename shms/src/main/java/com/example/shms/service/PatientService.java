package com.example.shms.service;
import com.example.shms.dto.PatientResponse;
import com.example.shms.entity.Patient;
import com.example.shms.repository.PatientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse getMyProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return new PatientResponse(
                patient.getPatientId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getAge(),
                patient.getGender(),
                patient.getMedicalHistory()
        );
    }
    public PatientResponse updateMyProfile(Patient updatedPatient) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        // Update only the permitted fields
        patient.setName(updatedPatient.getName());
        patient.setPhone(updatedPatient.getPhone());
        patient.setAge(updatedPatient.getAge());
        patient.setGender(updatedPatient.getGender());
        patient.setMedicalHistory(updatedPatient.getMedicalHistory());
        Patient savedPatient = patientRepository.save(patient);
        return new PatientResponse(
                savedPatient.getPatientId(),
                savedPatient.getName(),
                savedPatient.getEmail(),
                savedPatient.getPhone(),
                savedPatient.getAge(),
                savedPatient.getGender(),
                savedPatient.getMedicalHistory()
        );
    }

}
