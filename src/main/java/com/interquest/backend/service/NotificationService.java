package com.interquest.backend.service;

import com.interquest.backend.dto.NotificationDTO;
import com.interquest.backend.model.Notification;
import com.interquest.backend.model.Opportunity;
import com.interquest.backend.model.User;
import com.interquest.backend.repository.NotificationRepository;
import com.interquest.backend.repository.UserRepository;
import com.interquest.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository; // Need to fetch User object by username/email

    /**
     * Retrieves all notifications for the specified user (called by the Controller).
     */
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByUserId(String userEmail) {
        User user = (User) userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        List<Notification> notifications = notificationRepository.findAllByRecipientOrderByDateSentDesc(user);

        // Map Notification Entities to DTOs for the client
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marks a specific notification as read (called by the PUT /api/notifications/{id} endpoint).
     */
    @Transactional
    public void markAsRead(String userEmail, Long notificationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Notification notification = notificationRepository.findByIdAndRecipient(notificationId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found for this user."));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    // --- Internal/System Methods ---

    /**
     * Internal method to create and persist a notification (implements createNotification() [cite: 31]).
     * This would be called by the MatchingEngine or ScraperService when new opportunities are found.
     */
    @Transactional
    public Notification createAndSaveNotification(User recipient, String message, Opportunity relatedOpportunity) {
        Notification notification = new Notification(message, recipient, relatedOpportunity);
        return notificationRepository.save(notification);
    }

    /**
     * Helper method to map Entity to DTO.
     */
    private NotificationDTO convertToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .dateSent(notification.getDateSent())
                .isRead(notification.isRead())
                .opportunityId(notification.getOpportunity() != null ? notification.getOpportunity().getOppid() : null)
                .opportunityTitle(notification.getOpportunity() != null ? notification.getOpportunity().getTitle() : "General Alert")
                .build();
    }
}