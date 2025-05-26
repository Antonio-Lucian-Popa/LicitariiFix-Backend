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

    /**
     * Caută un utilizator după UUID-ul din Keycloak.
     */
    Optional<User> findByKeycloakId(UUID keycloakId);

    /**
     * Verifică dacă un user cu rolul dat este deja înregistrat cu un anumit Keycloak ID.
     */
    boolean existsByKeycloakIdAndRole(UUID keycloakId, String role);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
