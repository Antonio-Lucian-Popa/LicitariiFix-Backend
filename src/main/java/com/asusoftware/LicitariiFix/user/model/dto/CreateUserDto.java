package com.asusoftware.LicitariiFix.user.model.dto;

import com.asusoftware.LicitariiFix.user.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private UserRole role;
}