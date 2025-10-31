package com.interquest.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate; // Use LocalDate for deadlines

@Entity
@Table(name = "opportunities")
@Data
public class Opportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oppid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private String category;
}