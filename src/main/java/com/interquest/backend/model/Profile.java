package com.interquest.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "profiles")
@Data
public class Profile {

    // Matches 'profileId: int' from the class diagram [cite: 67]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    // Link back to User (one-to-one relationship)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    // Matches 'bio: String' from the class diagram [cite: 68]
    @Column(columnDefinition = "TEXT")
    private String bio;

    // Matches 'academicInterests: List<String>' from the class diagram [cite: 69]
    // Stored as a comma-separated String or in a separate table/JSON (simple list used here)
    @ElementCollection
    @CollectionTable(name = "profile_interests", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "interest")
    private List<String> academicInterests;

    // Note: editProfile() and getProfileDetails() are implemented via the Service layer. [cite: 70]
}