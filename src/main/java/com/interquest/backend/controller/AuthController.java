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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.registerNewUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String accessToken = authService.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }
}