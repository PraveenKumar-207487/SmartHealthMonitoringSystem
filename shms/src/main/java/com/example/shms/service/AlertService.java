
package com.example.shms.service;

import com.example.shms.entity.Alert;
import com.example.shms.entity.Patient;
import com.example.shms.entity.VitalStats;
import com.example.shms.repository.AlertRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {
    private final AlertRepository alertRepository;
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }
    public void checkVitalStats(Patient patient, VitalStats vitalStats) {
        List<String> messages = new ArrayList<>();

        if (vitalStats.getSystolicBP() != null &&
                (vitalStats.getSystolicBP() >= 180 ||
                        vitalStats.getSystolicBP() < 90)) {
            messages.add("Systolic blood pressure is outside the configured range");
        }
        if (vitalStats.getDiastolicBP() != null &&
                (vitalStats.getDiastolicBP() >= 120 ||
                        vitalStats.getDiastolicBP() < 60)) {
            messages.add("Diastolic blood pressure is outside the configured range");
        }
        if (vitalStats.getOxygenLevel() != null &&
                vitalStats.getOxygenLevel() < 90) {
            messages.add("Low blood oxygen level detected");
        }

        if (vitalStats.getTemperature() != null &&
                (vitalStats.getTemperature() >= 38.0 ||
                        vitalStats.getTemperature() < 35.0)) {
            messages.add("Body temperature is outside the configured range");
        }

        if (vitalStats.getHeartRate() != null &&
                (vitalStats.getHeartRate() < 50 ||
                        vitalStats.getHeartRate() > 120)) {
            messages.add("Heart rate is outside the configured range");
        }

        for (String message : messages) {
            Alert alert = new Alert();
            alert.setPatient(patient);
            alert.setVitalStats(vitalStats);
            alert.setMessage(message);
            alert.setSeverity("HIGH");
            alert.setRead(false);
            alert.setCreatedAt(LocalDateTime.now());
            alertRepository.save(alert);
        }
    }
    public Alert markAsRead(Integer alertId, Integer patientId) {
        Alert alert = alertRepository
                .findByAlertIdAndPatient_PatientId(alertId, patientId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setRead(true);

        return alertRepository.save(alert);
    }
}
