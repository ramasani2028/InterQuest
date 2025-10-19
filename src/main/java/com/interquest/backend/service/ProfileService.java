package com.interquest.backend.service;

import com.interquest.backend.dto.ProfileDTO;
import com.interquest.backend.dto.ProfileUpdateDTO;
import com.interquest.backend.exception.ResourceNotFoundException;
import com.interquest.backend.model.Profile;
import com.interquest.backend.model.User;
import com.interquest.backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.String;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    @Transactional
    public void createDefaultProfile(User newUser) {
        Profile profile = new Profile();
        profile.setUser(newUser);
        profile.setBio("Hello! I am a new student user of InterQuest.");
        profile.setAcademicInterests(Collections.emptyList());
        profileRepository.save(profile);
    }

    @Transactional
    public ProfileDTO updateProfile(String userEmail, ProfileUpdateDTO updateDTO) {
        Profile profile = profileRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(STR."Profile not found for: \{userEmail}"));
        profile.getUser().setName(updateDTO.getName());
        profile.setBio(updateDTO.getBio());
        profile.setAcademicInterests(updateDTO.getAcademicInterests());

        Profile updatedProfile = profileRepository.save(profile);

        return new ProfileDTO(
                userEmail,
                updatedProfile.getUser().getName(),
                updatedProfile.getBio(),
                updatedProfile.getAcademicInterests()
        );
    }

    @Transactional(readOnly = true)
    public ProfileDTO getProfileByEmail(String userEmail) {
        Profile profile = profileRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(STR."Profile not found for: \{userEmail}"));

        return new ProfileDTO(
                userEmail,
                profile.getUser().getName(),
                profile.getBio(),
                profile.getAcademicInterests()
        );
    }
}