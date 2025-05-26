package com.asusoftware.LicitariiFix.work_request.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "work_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkRequestStatus status;

    @Column(name = "client_id", nullable = false)
    private UUID clientId; // Referință decuplată

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
