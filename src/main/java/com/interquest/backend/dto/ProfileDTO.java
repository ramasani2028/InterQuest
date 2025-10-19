package com.interquest.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class ProfileDTO {
    private String email;
    private String name;
    private String bio;
    private List<String> academicInterests;

    public ProfileDTO(String email, String name, String bio, List<String> academicInterests) {
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.academicInterests = academicInterests;
    }
}