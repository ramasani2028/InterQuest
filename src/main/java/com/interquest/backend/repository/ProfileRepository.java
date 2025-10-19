package com.interquest.backend.repository;

import com.interquest.backend.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Custom query method required for the getProfileByEmail logic in ProfileService
    Optional<Profile> findByUserEmail(String email);
}