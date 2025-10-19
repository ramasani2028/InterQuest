package com.interquest.backend.repository;

import com.interquest.backend.model.Notification;
import com.interquest.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByRecipientOrderByDateSentDesc(User recipient);
    Optional<Notification> findByIdAndRecipient(Long id, User recipient);

    Notification save(Notification notification);
}