package com.interquest.backend.controller;

import com.interquest.backend.dto.JwtResponse;
import com.interquest.backend.dto.LoginRequest;
import com.interquest.backend.dto.RegisterRequest;
import com.interquest.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// Import your LoginRequest, RegisterRequest, and JwtResponse DTOs

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // For injecting services
public class AuthController {

    private final AuthService authService; // Your service for registration/JWT logic

    // U1: Register new users
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Validation check (e.g., if user exists)
        // Service method to save user and encrypt password
        authService.registerNewUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // U1: Login and generate JWT
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        // Authenticate user credentials
        String accessToken = authService.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }
}