package com.interquest.backend.service;

import com.interquest.backend.dto.OpportunityDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService {

    public List<OpportunityDTO> getMatchedOpportunities(String userEmail) {
        System.out.println(STR."Running matching for user: \{userEmail}");
        return List.of(
                new OpportunityDTO(1L, "AI Research Internship", "Java", "2026-01-01"),
                new OpportunityDTO(2L, "Spring Boot Scholarship", "Scholarship", "2025-12-31")
        );
    }


    public List<OpportunityDTO> filterOpportunities(Optional<String> keyword, Optional<String> category, Optional<String> deadline) {
        System.out.println(STR."Filtering with keyword: \{keyword.orElse("None")}");
        return List.of(
                new OpportunityDTO(3L, "Filtered Opportunity", "Web Dev", "2026-02-15")
        );
    }


    public OpportunityDTO getDetailsById(Long id) {
        return new OpportunityDTO(id, "Full Detail Opportunity", "Full Description", "2025-11-20");
    }

    public void logApplication(String userEmail, Long opportunityId) {
        System.out.println(STR."Logging application for user \{userEmail} to opportunity ID \{opportunityId}");
    }
}