package com.interquest.backend.controller;

import com.interquest.backend.dto.NotificationDTO;
import com.interquest.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<NotificationDTO> alerts = notificationService.getNotificationsByUserId(userDetails.getUsername());
        return ResponseEntity.ok(alerts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> markNotificationAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        notificationService.markAsRead(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}