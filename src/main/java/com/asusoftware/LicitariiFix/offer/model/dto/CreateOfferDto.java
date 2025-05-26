package com.asusoftware.LicitariiFix.offer.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOfferDto {
    private UUID workRequestId;
    private BigDecimal price;
    private String message;
}
