package com.interquest.backend.controller;

import com.interquest.backend.dto.OpportunityDTO;
import com.interquest.backend.service.OpportunityService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/matched")
    public ResponseEntity<List<OpportunityDTO>> getMatchedOpportunities(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<OpportunityDTO> opportunities = opportunityService.getMatchedOpportunities(userDetails.getUsername());
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OpportunityDTO>> searchOpportunities(
            @RequestParam(required = false) Optional<String> keyword,
            @RequestParam(required = false) Optional<String> category,
            @RequestParam(required = false) Optional<String> deadline) {

        List<OpportunityDTO> results = opportunityService.filterOpportunities(
                keyword,
                category,
                deadline
        );
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDTO> getOpportunityDetails(@PathVariable Long id) {
        OpportunityDTO opportunity = opportunityService.getDetailsById(id);
        return ResponseEntity.ok(opportunity);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> logApplication(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        opportunityService.logApplication(userDetails.getUsername(), id);
        return ResponseEntity.ok().build();
    }
}