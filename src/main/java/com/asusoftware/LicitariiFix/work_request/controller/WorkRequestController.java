package com.asusoftware.LicitariiFix.work_request.controller;

import com.asusoftware.LicitariiFix.work_request.model.dto.CreateWorkRequestDto;
import com.asusoftware.LicitariiFix.work_request.model.dto.WorkRequestDto;
import com.asusoftware.LicitariiFix.work_request.service.WorkRequestService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/work-requests")
@RequiredArgsConstructor
public class WorkRequestController {

    private final WorkRequestService workRequestService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<WorkRequestDto> createWorkRequest(
            @RequestPart("data") CreateWorkRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal Jwt jwt) {

        UUID keycloakId = UUID.fromString(jwt.getSubject());
        WorkRequestDto result = workRequestService.create(keycloakId, dto, files);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<WorkRequestDto>> getApprovedWorkRequests() {
        return ResponseEntity.ok(workRequestService.getAllApproved());
    }

    @GetMapping("/me")
    public ResponseEntity<List<WorkRequestDto>> getMyWorkRequests(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(workRequestService.getByClient(keycloakId));
    }

    @PostMapping("/{id}/approve")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> approve(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        workRequestService.approve(id, keycloakId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> reject(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        workRequestService.reject(id, keycloakId);
        return ResponseEntity.ok().build();
    }
}
