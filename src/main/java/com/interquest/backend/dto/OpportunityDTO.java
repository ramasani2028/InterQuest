package com.interquest.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDTO {
    private Long id; // oppId
    private String title;
    private String description;
    private String category;
    private String deadline; // Sent as a formatted string

    public OpportunityDTO(Long id, String title, String description, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}