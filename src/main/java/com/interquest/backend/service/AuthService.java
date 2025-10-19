package com.interquest.backend.service;

import com.interquest.backend.dto.LoginRequest;
import com.interquest.backend.dto.RegisterRequest;
import com.interquest.backend.exception.ResourceNotFoundException;
import com.interquest.backend.model.User;
import com.interquest.backend.model.Role;
import com.interquest.backend.repository.UserRepository;
import com.interquest.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService; // <-- ADDED ProfileService injection

    // U1: Register new users
    @Transactional
    public void registerNewUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        // 1. Encrypt password using BCrypt
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.ROLE_STUDENT);

        userRepository.save(newUser);

        // Logic: Create a default Profile entity (U2) and link it to newUser.
        profileService.createDefaultProfile(newUser); // <-- ADDED call to create profile
    }

    // U1: Login and generate JWT
    public String authenticateAndGenerateToken(LoginRequest request) {

        // 1. Authenticate credentials using the configured AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. If authentication succeeds, load the user to generate the token
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found after successful authentication."));

        // 3. Generate the token

        return jwtService.generateToken(user.getEmail());
    }
}