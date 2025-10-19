package com.interquest.backend.dto;

import lombok.Data;
import lombok.Builder; // ⬅️ Must be present
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

// ⬅️ These two annotations are required to make the Builder pattern work.
@Data
@Builder
@NoArgsConstructor // Often useful to have a default constructor
@AllArgsConstructor // Required for the Builder implementation to initialize all fields
public class NotificationDTO {

    private Long id;
    private String message;
    private LocalDateTime dateSent;
    private boolean isRead;
    private Long opportunityId;
    private String opportunityTitle;

}