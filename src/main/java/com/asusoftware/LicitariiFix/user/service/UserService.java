package com.asusoftware.LicitariiFix.user.service;


import com.asusoftware.LicitariiFix.config.KeycloakService;
import com.asusoftware.LicitariiFix.exception.UserAlreadyExistsException;
import com.asusoftware.LicitariiFix.exception.UserNotFoundException;
import com.asusoftware.LicitariiFix.user.model.User;
import com.asusoftware.LicitariiFix.user.model.dto.CreateUserDto;
import com.asusoftware.LicitariiFix.user.model.dto.LoginDto;
import com.asusoftware.LicitariiFix.user.model.dto.UserDto;
import com.asusoftware.LicitariiFix.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final ModelMapper mapper;

    /**
     * Înregistrare clasică – Owner sau Manager creează conturi din aplicație.
     */
    @Transactional
    public UserDto register(CreateUserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Există deja un utilizator cu acest email.");
        }

        String keycloakId = keycloakService.createKeycloakUser(dto);

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .keycloakId(keycloakId)
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }


    /**
     * Returnează userul autenticat după keycloakId.
     */
    public User getByKeycloakId(UUID keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("User cu keycloakId " + keycloakId + " nu a fost găsit"));
    }

    /**
     * Returnează userul după id-ul din aplicație.
     */
    public UserDto getById(UUID id) {
        return userRepository.findById(id)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User cu id " + id + " nu a fost găsit"));
    }

    /**
     * Login utilizator prin Keycloak.
     */
    public AccessTokenResponse login(LoginDto dto) {
        return keycloakService.loginUser(dto);
    }

    /**
     * Ștergere cont după keycloakId (soft delete sau hard).
     */
    @Transactional
    public void deleteByKeycloakId(UUID keycloakId) {
        userRepository.findByKeycloakId(keycloakId)
                .ifPresent(userRepository::delete);
    }
}
