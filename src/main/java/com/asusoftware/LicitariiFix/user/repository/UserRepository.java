package com.asusoftware.LicitariiFix.user.repository;

import com.asusoftware.LicitariiFix.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByKeycloakId(String keycloakId);

    boolean existsByKeycloakId(String keycloakId);
}
