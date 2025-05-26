package com.asusoftware.LicitariiFix.user.controller;

import com.asusoftware.LicitariiFix.user.model.User;
import com.asusoftware.LicitariiFix.user.model.dto.CreateUserDto;
import com.asusoftware.LicitariiFix.user.model.dto.LoginDto;
import com.asusoftware.LicitariiFix.user.model.dto.UserDto;
import com.asusoftware.LicitariiFix.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    /**
     * Înregistrare standard – doar OWNER / MANAGER poate crea useri manual.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserDto dto) {
        UserDto created = userService.register(dto);
        return ResponseEntity.ok(created);
    }


    /**
     * Login user – întoarce tokenul Keycloak.
     */
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    /**
     * Returnează userul curent după keycloakId.
     */
    @GetMapping("/by-keycloak/{keycloakId}")
    public ResponseEntity<UserDto> getByKeycloakId(@PathVariable UUID keycloakId) {
        User user = userService.getByKeycloakId(keycloakId);
        return ResponseEntity.ok(mapper.map(user, UserDto.class));
    }

    /**
     * Returnează userul curent după id-ul din aplicație.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        UserDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Șterge userul (hard delete).
     */
    @DeleteMapping("/by-keycloak/{keycloakId}")
    public ResponseEntity<Void> deleteByKeycloakId(@PathVariable UUID keycloakId) {
        userService.deleteByKeycloakId(keycloakId);
        return ResponseEntity.noContent().build();
    }
}
