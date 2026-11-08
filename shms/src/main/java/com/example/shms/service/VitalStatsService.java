package com.example.shms.service;
import com.example.shms.entity.Doctor;
import com.example.shms.entity.Patient;
import com.example.shms.entity.VitalStats;
import com.example.shms.repository.DoctorRepository;
import com.example.shms.repository.PatientRepository;
import com.example.shms.repository.VitalStatsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class VitalStatsService {
    private final VitalStatsRepository vitalStatsRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AlertService alertService;

    public VitalStatsService(
            VitalStatsRepository vitalStatsRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AlertService alertService) {
        this.vitalStatsRepository = vitalStatsRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.alertService = alertService;
    }

    // Existing patient-side functionality
    public VitalStats addVitalStats(VitalStats vitalStats) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Patient not found"));

        vitalStats.setVitalId(null);
        vitalStats.setPatient(patient);
        vitalStats.setRecordedAt(LocalDateTime.now());

        VitalStats saved = vitalStatsRepository.save(vitalStats);
        alertService.checkVitalStats(patient, saved);

        return saved;
    }

    // New doctor-side functionality
    public VitalStats addVitalStatsForPatient(
            Integer patientId, VitalStats vitalStats) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Doctor not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Patient not found"));

        if (patient.getDoctor() == null
                || !patient.getDoctor().getDoctorId()
                .equals(doctor.getDoctorId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "This patient is not assigned to you");
        }

        vitalStats.setVitalId(null);
        vitalStats.setPatient(patient);
        vitalStats.setRecordedAt(LocalDateTime.now());

        VitalStats saved = vitalStatsRepository.save(vitalStats);
        alertService.checkVitalStats(patient, saved);

        return saved;
    }

    // Existing patient-side functionality
    public List<VitalStats> getMyVitalStats() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Patient not found"));

        return vitalStatsRepository
                .findByPatient_PatientId(patient.getPatientId());
    }
}
