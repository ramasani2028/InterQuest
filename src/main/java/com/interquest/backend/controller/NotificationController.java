package com.interquest.backend.controller;

import com.interquest.backend.dto.NotificationDTO; // DTO for notification data
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

    // 1. GET /api/notifications
    // Retrieves all notifications for the logged-in user (U5).
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {

        // The service uses the authenticated user's email/username to fetch their notifications.
        List<NotificationDTO> alerts = notificationService.getNotificationsByUserId(userDetails.getUsername());

        return ResponseEntity.ok(alerts);
    }

    // 2. PUT /api/notifications/{id}
    // Marks a specific notification as read.
    @PutMapping("/{id}")
    public ResponseEntity<Void> markNotificationAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        // The service validates that the notification belongs to the authenticated user,
        // then marks it as read/interacted with (U5 - Log Notification Activity).
        notificationService.markAsRead(userDetails.getUsername(), id);

        // Return a 204 No Content status on successful update
        return ResponseEntity.noContent().build();
    }
}