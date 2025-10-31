package com.interquest.backend.controller;

import com.interquest.backend.dto.OpportunityCreationDTO;
import com.interquest.backend.dto.OpportunityDTO;
import com.interquest.backend.dto.UserDTO;
import com.interquest.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final  AdminService adminService;

    @PostMapping("/scrape/run")
    public ResponseEntity<String> triggerWebScraping() {
        adminService.runOpportunityDiscovery();
        return ResponseEntity.ok("Scraping initiated successfully");
    }

    @PostMapping("/opportunities")
    public ResponseEntity<OpportunityDTO> createOpportunity(@RequestBody OpportunityCreationDTO dto) {
        OpportunityDTO newOpp = adminService.createOpportunity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOpp);
    }

    @PutMapping("/opportunities/{id}")
    public ResponseEntity<OpportunityDTO> updateOpportunity(@PathVariable Long id,
                                                            @RequestBody OpportunityCreationDTO dto) {
        OpportunityDTO updatedOpp = adminService.updateOpportunity(id, dto);
        return ResponseEntity.ok(updatedOpp);
    }

    @DeleteMapping("/opportunities/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        adminService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}