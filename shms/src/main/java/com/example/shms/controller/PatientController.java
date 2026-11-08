package com.example.shms.controller;
import com.example.shms.dto.PatientResponse;
import com.example.shms.entity.Patient;
import com.example.shms.service.PatientService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("/me")
    public PatientResponse getMyProfile() {
        return patientService.getMyProfile();
    }
    @PutMapping("/me")
    public PatientResponse updateMyProfile(
            @RequestBody Patient updatedPatient) {
        return patientService.updateMyProfile(updatedPatient);
    }
}
