
package com.example.shms.config;

import com.example.shms.security.JwtAuthenticationFilter;
import com.example.shms.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Thymeleaf pages and CSS
                        .requestMatchers(
                                "/", "/login", "/register",
                                "/patient-dashboard", "/doctor-dashboard",
                                "/css/**"
                        ).permitAll()

                        // Public authentication APIs
                        .requestMatchers(
                                "/auth/register", "/auth/login",
                                "/auth/register/doctor", "/auth/login/doctor"
                        ).permitAll()

                        // Patient APIs
                        .requestMatchers("/patients/**").hasRole("PATIENT")
                        .requestMatchers("/vitals/patient/**").hasRole("DOCTOR")
                        .requestMatchers("/vitals/**").hasRole("PATIENT")
                        .requestMatchers("/alerts/**").hasRole("PATIENT")

                        // Doctor APIs
                        .requestMatchers("/doctors/**").hasRole("DOCTOR")
                        .requestMatchers("/doctor/dashboard/**").hasRole("DOCTOR")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
