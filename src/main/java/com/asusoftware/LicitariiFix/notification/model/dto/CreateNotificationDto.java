package com.asusoftware.LicitariiFix.notification.model.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationDto {
    private String message;
    private UUID userId;
}
