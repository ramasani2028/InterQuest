package com.interquest.backend.controller;

import com.interquest.backend.dto.ProfileDTO;
import com.interquest.backend.dto.ProfileUpdateDTO;
import com.interquest.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class StudentProfileController {

    private  final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        ProfileDTO profile = profileService.getProfileByEmail(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody ProfileUpdateDTO updateDTO) {
        ProfileDTO updatedProfile = profileService.updateProfile(userDetails.getUsername(), updateDTO);
        return ResponseEntity.ok(updatedProfile);
    }
}