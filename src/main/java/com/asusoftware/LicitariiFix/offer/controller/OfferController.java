package com.asusoftware.LicitariiFix.offer.controller;

import com.asusoftware.LicitariiFix.offer.model.dto.CreateOfferDto;
import com.asusoftware.LicitariiFix.offer.model.dto.OfferDto;
import com.asusoftware.LicitariiFix.offer.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferDto> sendOffer(@RequestBody CreateOfferDto dto,
                                              @AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(offerService.create(keycloakId, dto));
    }

    @GetMapping("/work/{workRequestId}")
    public ResponseEntity<List<OfferDto>> getOffersByWorkRequest(@PathVariable UUID workRequestId) {
        return ResponseEntity.ok(offerService.getAllByWork(workRequestId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<OfferDto>> getMyOffers(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(offerService.getByMeserias(keycloakId));
    }
}