package com.example.shms.controller;
import com.example.shms.entity.Doctor;
import com.example.shms.service.DoctorService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping("/me")
    public Doctor getMyProfile() {
        return doctorService.getMyProfile();
    }
    @PutMapping("/me")
    public Doctor updateMyProfile(@RequestBody Doctor updatedDoctor) {
        return doctorService.updateMyProfile(updatedDoctor);
    }
}
