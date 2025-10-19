package com.interquest.backend.controller;

import com.interquest.backend.dto.OpportunityDTO; // DTO for response data
import com.interquest.backend.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final  OpportunityService opportunityService;

    // 1. GET /api/opportunities/matched
    // Retrieves a personalized list of opportunities matched to the user's interests (U4).
    @GetMapping("/matched")
    public ResponseEntity<List<OpportunityDTO>> getMatchedOpportunities(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<OpportunityDTO> opportunities = opportunityService.getMatchedOpportunities(userDetails.getUsername());

        return ResponseEntity.ok(opportunities);
    }

    // 2. GET /api/opportunities/search
    // Retrieves all opportunities with optional query parameters for search/filtering (U4).
    @GetMapping("/search")
    public ResponseEntity<List<OpportunityDTO>> searchOpportunities(
            @RequestParam(required = false) Optional<String> keyword,
            @RequestParam(required = false) Optional<String> category,
            @RequestParam(required = false) Optional<String> deadline) {

        // The service method handles filtering based on the provided parameters[cite: 598].
        List<OpportunityDTO> results = opportunityService.filterOpportunities(
                keyword,
                category,
                deadline
        );

        return ResponseEntity.ok(results);
    }

    // 3. GET /api/opportunities/{id}
    // Retrieves full details for a single opportunity.
    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDTO> getOpportunityDetails(@PathVariable Long id) {

        OpportunityDTO opportunity = opportunityService.getDetailsById(id);

        return ResponseEntity.ok(opportunity);
    }

    // 4. POST /api/opportunities/{id}/apply
    // Logs the user's application action (If tracking application status is a requirement).
    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> logApplication(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        // The service logs the action to the database[cite: 653, 699].
        opportunityService.logApplication(userDetails.getUsername(), id);

        return ResponseEntity.ok().build();
    }
}