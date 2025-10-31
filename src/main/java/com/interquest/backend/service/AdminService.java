package com.interquest.backend.service;

import com.interquest.backend.dto.OpportunityCreationDTO;
import com.interquest.backend.dto.OpportunityDTO;
import com.interquest.backend.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminService {

    private final ScraperService scraperService;

    public AdminService(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    public void runOpportunityDiscovery() {
        String targetUrl = "https://example-scholarships.com/listings"; // Replace with real URL

        int count = scraperService.fetchAndSaveOpportunities();

        System.out.println("Admin triggered opportunity scraping. Saved " + count + " new opportunities.");

    }

    public OpportunityDTO createOpportunity(OpportunityCreationDTO dto) {
        System.out.println(STR."Admin manually created opportunity: \{dto.getTitle()}");
        return new OpportunityDTO(99L, dto.getTitle(), dto.getCategory(), dto.getDeadline());
    }

    public OpportunityDTO updateOpportunity(Long id, OpportunityCreationDTO dto) {
        System.out.println(STR."Admin updated opportunity ID: \{id}");
        return new OpportunityDTO(id, dto.getTitle(), dto.getCategory(), dto.getDeadline());
    }

    public void deleteOpportunity(Long id) {
        System.out.println(STR."Admin deleted opportunity ID: \{id}");
    }

    public List<UserDTO> findAllUsers() {
        System.out.println("Admin fetching all users (U6).");
        return List.of(
                new UserDTO(1L, "admin@interquest.com", "Admin"),
                new UserDTO(2L, "student@test.com", "Student")
        );
    }
}