package com.interquest.backend.controller;

import com.interquest.backend.dto.ProfileDTO;
import com.interquest.backend.dto.ProfileUpdateDTO;
import com.interquest.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
// Import your ProfileDTO, ProfileUpdateDTO

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
// Spring Security ensures only authenticated users can access this controller
public class StudentProfileController {

    private  ProfileService profileService;

    // U2: View Profile
    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        // Use userDetails.getUsername() (the email) to fetch the profile
        ProfileDTO profile = profileService.getProfileByEmail(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    // U2: Update Profile (including interests)
    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody ProfileUpdateDTO updateDTO) {
        ProfileDTO updatedProfile = profileService.updateProfile(userDetails.getUsername(), updateDTO);
        return ResponseEntity.ok(updatedProfile);
    }
}