package com.asusoftware.LicitariiFix.work_request.model;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkImage {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID workRequestId; // Referință la lucrarea asociată

    @Column(nullable = false)
    private String photoUrl;

    private LocalDateTime uploadedAt = LocalDateTime.now();
}