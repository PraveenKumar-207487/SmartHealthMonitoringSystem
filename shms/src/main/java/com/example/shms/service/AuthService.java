
package com.example.shms.service;

import com.example.shms.dto.AuthResponse;
import com.example.shms.dto.LoginRequest;
import com.example.shms.dto.RegisterRequest;
import com.example.shms.entity.Doctor;
import com.example.shms.entity.Patient;
import com.example.shms.entity.User;
import com.example.shms.repository.DoctorRepository;
import com.example.shms.repository.PatientRepository;
import com.example.shms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(
            UserRepository userRepository,
            PatientRepository patientRepository,
            PasswordEncoder passwordEncoder,JwtService jwtService,DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.doctorRepository=doctorRepository;
        this.jwtService=jwtService;
    }

    @Transactional
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Patient email is already registered");
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                "PATIENT",
                request.getName()
        );

        userRepository.save(user);

        Patient patient = new Patient(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getAge(),
                request.getGender(),
                request.getMedicalHistory()
        );
        patientRepository.save(patient);

        return "Patient registered successfully";
    }


    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtService.generateToken(
                user.getEmail(), user.getRole());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole()
        );
    }

    @Transactional
    public String registerDoctor(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Doctor email is already registered");
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                "DOCTOR",
                request.getName()
        );

        userRepository.save(user);

        Doctor doctor = new Doctor(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getSpecialization()
        );

        doctorRepository.save(doctor);

        return "Doctor registered successfully";
    }

}
