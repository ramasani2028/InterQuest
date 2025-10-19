package com.interquest.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate; // Use LocalDate for deadlines

@Entity
@Table(name = "opportunities") // Correctly mapped to the 'opportunities' table
@Data
public class Opportunity {

    // Matches 'oppId: int' from the class diagram [cite: 59]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oppid;

    @Column(nullable = false)
    private String title; // Matches 'title: String' from the class diagram [cite: 60]

    // Matches 'description: String' from the class diagram [cite: 61]
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // Matches 'deadline: Date' from the class diagram [cite: 61]
    @Column(nullable = false)
    private LocalDate deadline;

    // Matches 'category: String' from the class diagram [cite: 61]
    @Column(nullable = false)
    private String category;

    // Note: The getDetails() method in the class diagram is implemented
    // implicitly via the getter methods and the service layer. [cite: 62]
}