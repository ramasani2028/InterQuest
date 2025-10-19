package com.interquest.backend.service;

import com.interquest.backend.dto.ProfileDTO;
import com.interquest.backend.dto.ProfileUpdateDTO;
import com.interquest.backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.lang.String;
import java.util.Collections;
import java.util.List;

@Service
public class ProfileService {

    // Dependencies: UserRepository, ProfileRepository, ProfileMapper

    // U2: Update Profile
    public ProfileDTO updateProfile(String userEmail, ProfileUpdateDTO updateDTO) {
        // Logic:
        // 1. Fetch: Retrieve the User/Profile entity using userEmail.
        // 2. Validate: Check if data in updateDTO is valid (U2 - Validate Information).
        // 3. Update: Apply changes (name, bio, academic interests) to the Profile entity.
        // 4. Save: Save the updated Profile entity (U2 - Update Profile Data).
        // 5. Map: Convert the saved entity back to a ProfileDTO for the response.

        // Placeholder for mapping and return:
        System.out.println("Updating profile for: " + userEmail + " with interests: " + updateDTO.getAcademicInterests());
        return new ProfileDTO(userEmail, "Updated Name", "Updated Bio", Collections.singletonList(String.valueOf(updateDTO.getAcademicInterests())));
    }

    // U2: Get Profile Details
    public ProfileDTO getProfileByEmail(String userEmail) {
        // Logic:
        // 1. Fetch: Retrieve the Profile entity using userEmail.
        // 2. Map: Convert the Profile entity to a ProfileDTO.

        // Placeholder for profile details:
        if (userEmail.equals("test@example.com")) {
            return new ProfileDTO(userEmail, "Test Student", "Seeking internships in AI.", List.of("AI/ML", "Java"));
        } else {
            throw new ResourceNotFoundException(STR."Profile not found for \{userEmail}");
        }
    }
}