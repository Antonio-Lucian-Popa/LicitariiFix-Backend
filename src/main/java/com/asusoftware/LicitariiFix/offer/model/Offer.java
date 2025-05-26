package com.asusoftware.LicitariiFix.offer.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "work_request_id", nullable = false)
    private UUID workRequestId;

    @Column(name = "meserias_id", nullable = false)
    private UUID meseriasId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}