package com.interquest.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String tokenType = "Bearer"; // Standard type for JWTs


    public JwtResponse(String accessToken) {
    }
}