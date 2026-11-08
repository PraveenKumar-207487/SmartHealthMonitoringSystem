
package com.example.shms.controller;

import com.example.shms.dto.AuthResponse;
import com.example.shms.dto.RegisterRequest;
import com.example.shms.dto.LoginRequest;
import com.example.shms.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/register/doctor")
    public String registerDoctor(@RequestBody RegisterRequest request) {
        return authService.registerDoctor(request);
    }
    @PostMapping("/login/doctor")
    public AuthResponse doctorLogin(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
