package com.interquest.backend.dto;

import lombok.Data;
import lombok.Builder; // ⬅️ Must be present
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    private String message;
    private LocalDateTime dateSent;
    private boolean isRead;
    private Long opportunityId;
    private String opportunityTitle;

}