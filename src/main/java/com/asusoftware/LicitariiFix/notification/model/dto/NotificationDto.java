package com.asusoftware.LicitariiFix.notification.model.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private UUID id;
    private UUID userId;
    private String message;
    private boolean isRead;
    private Instant createdAt;
}