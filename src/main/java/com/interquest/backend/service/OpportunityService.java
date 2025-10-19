package com.interquest.backend.service;

import com.interquest.backend.dto.OpportunityDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService {

    // Dependencies: OpportunityRepository, ProfileRepository, MatchingEngine (or MatchingEngineService)

    // U4: Get Personalized Matches
    public List<OpportunityDTO> getMatchedOpportunities(String userEmail) {
        // Logic:
        // 1. Fetch: Get the Profile/Interests of the user using userEmail.
        // 2. Match: Call the MatchingEngine.matchOpportunities(profile) method.
        // 3. Return: Map the results to OpportunityDTOs.
        System.out.println(STR."Running matching for user: \{userEmail}");
        return List.of(
                new OpportunityDTO(1L, "AI Research Internship", "Java", "2026-01-01"),
                new OpportunityDTO(2L, "Spring Boot Scholarship", "Scholarship", "2025-12-31")
        );
    }

    // U4: Search/Filter
    public List<OpportunityDTO> filterOpportunities(Optional<String> keyword, Optional<String> category, Optional<String> deadline) {
        // Logic:
        // 1. Query: Build a dynamic JPA query (or use a custom search Repository method).
        // 2. Apply: Use keyword/category/deadline parameters to filter results (U4).
        // 3. Return: Map the filtered Opportunity entities to DTOs.
        System.out.println(STR."Filtering with keyword: \{keyword.orElse("None")}");
        return List.of(
                new OpportunityDTO(3L, "Filtered Opportunity", "Web Dev", "2026-02-15")
        );
    }

    // U4: Get Details
    public OpportunityDTO getDetailsById(Long id) {
        // Logic:
        // 1. Fetch: Use OpportunityRepository.findById(id).
        // 2. Return: Map the entity to a detailed OpportunityDTO.
        return new OpportunityDTO(id, "Full Detail Opportunity", "Full Description", "2025-11-20");
    }

    // Apply (Optional)
    public void logApplication(String userEmail, Long opportunityId) {
        // Logic:
        // 1. Log: Record the user's action in a separate Application/Log entity.
        System.out.println(STR."Logging application for user \{userEmail} to opportunity ID \{opportunityId}");
    }
}