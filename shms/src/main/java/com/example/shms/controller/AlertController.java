
package com.example.shms.controller;

import com.example.shms.entity.Alert;
import com.example.shms.entity.Patient;
import com.example.shms.repository.AlertRepository;
import com.example.shms.repository.PatientRepository;
import com.example.shms.service.AlertService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/alerts")
public class AlertController {
    private final AlertRepository alertRepository;
    private final PatientRepository patientRepository;
    private final AlertService alertService;

    public AlertController(
            AlertRepository alertRepository,
            PatientRepository patientRepository,
            AlertService alertService) {
        this.alertRepository = alertRepository;
        this.patientRepository = patientRepository;
        this.alertService = alertService;
    }
    @GetMapping("/me")
    public List<Alert> getMyAlerts() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return alertRepository
                .findByPatient_PatientIdOrderByCreatedAtDesc(
                        patient.getPatientId());
    }

    @PutMapping("/{alertId}/read")
    public Alert markAsRead(@PathVariable Integer alertId) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return alertService.markAsRead(
                alertId, patient.getPatientId());
    }
}
