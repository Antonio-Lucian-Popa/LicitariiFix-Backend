package com.asusoftware.LicitariiFix.work_request.model.dto;

import com.asusoftware.LicitariiFix.work_request.model.WorkRequestStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkRequestDto {
    private UUID id;
    private String title;
    private String description;
    private String location;
    private String[] photos;
    private WorkRequestStatus status;
    private UUID clientId;
    private Instant createdAt;
}
