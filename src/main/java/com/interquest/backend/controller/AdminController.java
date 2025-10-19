package com.interquest.backend.controller;

import com.interquest.backend.dto.OpportunityCreationDTO;
import com.interquest.backend.dto.OpportunityDTO;
import com.interquest.backend.dto.UserDTO;
import com.interquest.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Import OpportunityDTO, OpportunityCreationDTO, UserDTO

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
// Requires ROLE_ADMIN for ALL methods in this controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private AdminService adminService;

    // U3/U6: Manually Trigger Web Scraping
    @PostMapping("/scrape/run")
    public ResponseEntity<String> triggerWebScraping() {
        // Service method initiates Jsoup scraping process
        adminService.runOpportunityDiscovery();
        return ResponseEntity.ok("Scraping initiated successfully");
    }

    // U6: Create New Opportunity (Manual Entry)
    @PostMapping("/opportunities")
    public ResponseEntity<OpportunityDTO> createOpportunity(@RequestBody OpportunityCreationDTO dto) {
        OpportunityDTO newOpp = adminService.createOpportunity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOpp);
    }

    // U6: Update Opportunity
    @PutMapping("/opportunities/{id}")
    public ResponseEntity<OpportunityDTO> updateOpportunity(@PathVariable Long id,
                                                            @RequestBody OpportunityCreationDTO dto) {
        OpportunityDTO updatedOpp = adminService.updateOpportunity(id, dto);
        return ResponseEntity.ok(updatedOpp);
    }

    // U6: Delete Opportunity
    @DeleteMapping("/opportunities/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        adminService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

    // U6: Manage User Accounts (e.g., viewing all users)
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}