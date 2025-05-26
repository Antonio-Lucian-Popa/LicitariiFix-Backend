package com.asusoftware.LicitariiFix.offer.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private UUID id;
    private UUID workRequestId;
    private UUID meseriasId;
    private BigDecimal price;
    private String message;
    private Instant createdAt;
}
