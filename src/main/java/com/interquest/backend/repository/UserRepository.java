package com.interquest.backend.repository;

import com.interquest.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Required by NotificationService to find the recipient
    Optional<User> findByEmail(String email);
}