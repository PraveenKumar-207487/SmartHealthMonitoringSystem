
package com.example.shms.controller;

import com.example.shms.entity.Alert;
import com.example.shms.entity.Doctor;
import com.example.shms.entity.Patient;
import com.example.shms.entity.VitalStats;
import com.example.shms.repository.AlertRepository;
import com.example.shms.repository.DoctorRepository;
import com.example.shms.repository.PatientRepository;
import com.example.shms.repository.VitalStatsRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/doctor/dashboard")
public class DoctorDashboardController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final VitalStatsRepository vitalStatsRepository;
    private final AlertRepository alertRepository;
    public DoctorDashboardController(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            VitalStatsRepository vitalStatsRepository,
            AlertRepository alertRepository) {

        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.vitalStatsRepository = vitalStatsRepository;
        this.alertRepository = alertRepository;
    }

    // Assign an unassigned patient to the logged-in doctor
    @PutMapping("/assign/{patientId}")
    public Patient assignPatient(
            @PathVariable Integer patientId,
            Authentication authentication) {

        String email = authentication.getName();

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient not found"));

        if (patient.getDoctor() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Patient is already assigned to a doctor");
        }

        patient.setDoctor(doctor);

        return patientRepository.save(patient);
    }

    // Get patients assigned to the logged-in doctor
    @GetMapping("/patients")
    public List<Patient> getAssignedPatients(
            Authentication authentication) {

        Doctor doctor = getLoggedInDoctor(authentication);

        return patientRepository.findByDoctor_DoctorId(
                doctor.getDoctorId());
    }

    // Get all vital readings for an assigned patient,
    // with the latest reading displayed first
    @GetMapping("/patients/{patientId}/vitals")
    public List<VitalStats> getPatientVitals(
            @PathVariable Integer patientId,
            Authentication authentication) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient not found"));

        verifyPatientAssignment(patient, doctor);

        return vitalStatsRepository
                .findByPatient_PatientIdOrderByRecordedAtDesc(patientId);
    }

    // Get alerts for an assigned patient, newest first
    @GetMapping("/patients/{patientId}/alerts")
    public List<Alert> getPatientAlerts(
            @PathVariable Integer patientId,
            Authentication authentication) {

        Doctor doctor = getLoggedInDoctor(authentication);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient not found"));

        verifyPatientAssignment(patient, doctor);

        return alertRepository
                .findByPatient_PatientIdOrderByCreatedAtDesc(patientId);
    }

    // Get patients who have not yet been assigned to a doctor
    @GetMapping("/unassigned-patients")
    public List<Patient> getUnassignedPatients() {
        return patientRepository.findByDoctorIsNull();
    }

    // Find the currently logged-in doctor
    private Doctor getLoggedInDoctor(Authentication authentication) {

        return doctorRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));
    }

    // Ensure the patient belongs to the logged-in doctor
    private void verifyPatientAssignment(
            Patient patient, Doctor doctor) {

        if (patient.getDoctor() == null
                || !patient.getDoctor().getDoctorId()
                .equals(doctor.getDoctorId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "This patient is not assigned to you");
        }
    }
}
