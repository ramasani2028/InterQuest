package com.interquest.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityCreationDTO {
    private String title;
    private String description;
    private String category;
    private String deadline; // Input date string

}