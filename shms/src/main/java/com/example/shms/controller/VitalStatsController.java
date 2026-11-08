package com.example.shms.controller;
import com.example.shms.entity.VitalStats;
import com.example.shms.service.VitalStatsService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/vitals")
public class VitalStatsController {
    private final VitalStatsService vitalStatsService;
    public VitalStatsController(VitalStatsService vitalStatsService) {
        this.vitalStatsService = vitalStatsService;
    }
    @PostMapping
    public VitalStats addVitalStats(@RequestBody VitalStats vitalStats) {
        return vitalStatsService.addVitalStats(vitalStats);
    }
    @GetMapping("/me")
    public List<VitalStats> getMyVitalStats() {
        return vitalStatsService.getMyVitalStats();
    }

    @PostMapping("/patient/{patientId}")
    public VitalStats addVitalStatsForPatient(
            @PathVariable Integer patientId,
            @RequestBody VitalStats vitalStats) {

        return vitalStatsService.addVitalStatsForPatient(
                patientId, vitalStats);
    }

}
