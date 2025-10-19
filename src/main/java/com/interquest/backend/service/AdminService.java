package com.interquest.backend.service;

import com.interquest.backend.dto.OpportunityCreationDTO;
import com.interquest.backend.dto.OpportunityDTO;
import com.interquest.backend.dto.UserDTO;
import com.interquest.backend.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("hasRole('ADMIN')") // Enforce Admin role at the service layer
public class AdminService {

    private final ScraperService scraperService;

    public AdminService(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    // U3/U6: Manually Trigger Discovery
    public void runOpportunityDiscovery() {
        // Logic: Define the URL(s) to scrape
        String targetUrl = "https://example-scholarships.com/listings"; // Replace with real URL

        // 1. Trigger the scraping process (U3)
        int count = scraperService.fetchAndSaveOpportunities();

        System.out.println("Admin triggered opportunity scraping. Saved " + count + " new opportunities.");

        // Logic: After saving, trigger matching/notification updates for existing users
        // matchingService.triggerMatchAfterDiscovery();
    }

    // U6: Create Opportunity (Manual Admin Entry)
    public OpportunityDTO createOpportunity(OpportunityCreationDTO dto) {
        // Logic:
        // 1. Map: Convert the DTO to an Opportunity entity.
        // 2. Save: Use OpportunityRepository.save() (U6 - Apply Changes).
        System.out.println(STR."Admin manually created opportunity: \{dto.getTitle()}");
        return new OpportunityDTO(99L, dto.getTitle(), dto.getCategory(), dto.getDeadline());
    }

    // U6: Update Opportunity
    public OpportunityDTO updateOpportunity(Long id, OpportunityCreationDTO dto) {
        // Logic:
        // 1. Fetch: Retrieve the Opportunity entity by ID.
        // 2. Update: Apply changes from DTO.
        // 3. Save: Use OpportunityRepository.save() (U6 - Apply Changes).
        System.out.println(STR."Admin updated opportunity ID: \{id}");
        return new OpportunityDTO(id, dto.getTitle(), dto.getCategory(), dto.getDeadline());
    }

    // U6: Delete Opportunity
    public void deleteOpportunity(Long id) {
        // Logic:
        // 1. Delete: Use OpportunityRepository.deleteById(id) (U6 - Apply Changes).
        System.out.println(STR."Admin deleted opportunity ID: \{id}");
    }

    // U6: Find All Users
    public List<UserDTO> findAllUsers() {
        // Logic:
        // 1. Fetch: Use UserRepository.findAll().
        // 2. Map: Convert User entities to UserDTOs (excluding passwords).
        System.out.println("Admin fetching all users (U6).");
        return List.of(
                new UserDTO(1L, "admin@interquest.com", "Admin"),
                new UserDTO(2L, "student@test.com", "Student")
        );
    }
}