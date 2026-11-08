
package com.example.shms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "vital_stats")
public class VitalStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vitalId;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    private Double systolicBP;
    private Double diastolicBP;
    private Double oxygenLevel;
    private Double temperature;
    private Double heartRate;
    private LocalDateTime recordedAt;
    public VitalStats() {
    }
    public Integer getVitalId() {
        return vitalId;
    }
    public void setVitalId(Integer vitalId) {
        this.vitalId = vitalId;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Double getSystolicBP() {
        return systolicBP;
    }
    public void setSystolicBP(Double systolicBP) {
        this.systolicBP = systolicBP;
    }
    public Double getDiastolicBP() {
        return diastolicBP;
    }
    public void setDiastolicBP(Double diastolicBP) {
        this.diastolicBP = diastolicBP;
    }
    public Double getOxygenLevel() {
        return oxygenLevel;
    }
    public void setOxygenLevel(Double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }
    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public Double getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(Double heartRate) {
        this.heartRate = heartRate;
    }
    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
