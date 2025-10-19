package com.interquest.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateSent;

    @Column(nullable = false)
    private boolean isRead = false;

    // Relationship to the User who receives the notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User recipient;

    // Relationship to the Opportunity that triggered the notification (if applicable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;

    public Notification(String message, User recipient, Opportunity opportunity) {
        this.message = message;
        this.recipient = recipient;
        this.opportunity = opportunity;
        this.dateSent = LocalDateTime.now();
        this.isRead = false;
    }
}