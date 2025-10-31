package com.interquest.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {
    private String name;
    private String bio;
    private List<String> academicInterests;

    public List<String> getAcademicInterests() {
        return academicInterests;
    }
}