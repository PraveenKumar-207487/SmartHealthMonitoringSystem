package com.example.shms.repository;
import com.example.shms.entity.VitalStats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VitalStatsRepository extends JpaRepository<VitalStats, Integer> {
    List<VitalStats> findByPatient_PatientId(Integer patientId);
    List<VitalStats> findByPatient_PatientIdOrderByRecordedAtDesc(
            Integer patientId);
}
