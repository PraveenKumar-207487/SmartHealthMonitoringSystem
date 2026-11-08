
package com.example.shms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alertId;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "vital_id", nullable = false)
    private VitalStats vitalStats;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String severity;
    private boolean isRead = false;
    private LocalDateTime createdAt;
    public Alert() {
    }
    public Integer getAlertId() {
        return alertId;
    }
    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public VitalStats getVitalStats() {
        return vitalStats;
    }
    public void setVitalStats(VitalStats vitalStats) {
        this.vitalStats = vitalStats;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
