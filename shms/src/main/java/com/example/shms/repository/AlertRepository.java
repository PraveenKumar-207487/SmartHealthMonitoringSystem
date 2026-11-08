
package com.example.shms.repository;
import com.example.shms.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
    List<Alert> findByPatient_PatientIdOrderByCreatedAtDesc(Integer patientId);
    Optional<Alert> findByAlertIdAndPatient_PatientId(
            Integer alertId, Integer patientId);
}
