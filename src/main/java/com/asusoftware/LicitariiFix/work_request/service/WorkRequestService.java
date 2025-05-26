package com.asusoftware.LicitariiFix.work_request.service;

import com.asusoftware.LicitariiFix.user.model.User;
import com.asusoftware.LicitariiFix.user.model.UserRole;
import com.asusoftware.LicitariiFix.user.repository.UserRepository;
import com.asusoftware.LicitariiFix.work_request.model.WorkImage;
import com.asusoftware.LicitariiFix.work_request.model.WorkRequest;
import com.asusoftware.LicitariiFix.work_request.model.WorkRequestStatus;
import com.asusoftware.LicitariiFix.work_request.model.dto.CreateWorkRequestDto;
import com.asusoftware.LicitariiFix.work_request.model.dto.WorkRequestDto;
import com.asusoftware.LicitariiFix.work_request.repository.WorkImageRepository;
import com.asusoftware.LicitariiFix.work_request.repository.WorkRequestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkRequestService {

    private final WorkRequestRepository workRequestRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final WorkImageRepository workImageRepository;
    private final ModelMapper mapper;

    public WorkRequestDto create(UUID clientId, CreateWorkRequestDto dto, List<MultipartFile> files) {
        WorkRequest entity = WorkRequest.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .status(WorkRequestStatus.PENDING)
                .clientId(clientId)
                .build();
        workRequestRepository.save(entity);

        List<WorkImage> savedImages = files.stream()
                .map(file -> {
                    String url = fileStorageService.saveFile(file, entity.getId());
                    return WorkImage.builder()
                            .workRequestId(entity.getId())
                            .photoUrl(url)
                            .build();
                })
                .collect(Collectors.toList());
        workImageRepository.saveAll(savedImages);

        WorkRequestDto dtoResponse = mapper.map(entity, WorkRequestDto.class);
        dtoResponse.setPhotos(savedImages.stream()
                .map(WorkImage::getPhotoUrl)
                .collect(Collectors.toList()));
        return dtoResponse;
    }

    public List<WorkRequestDto> getAllApproved() {
        return workRequestRepository.findAllByStatus(WorkRequestStatus.APPROVED)
                .stream().map(w -> {
                    WorkRequestDto dto = mapper.map(w, WorkRequestDto.class);
                    dto.setPhotos(workImageRepository.findAllByWorkRequestId(w.getId())
                            .stream().map(WorkImage::getPhotoUrl).collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
    }

    public List<WorkRequestDto> getByClient(UUID clientId) {
        return workRequestRepository.findAllByClientId(clientId)
                .stream().map(w -> {
                    WorkRequestDto dto = mapper.map(w, WorkRequestDto.class);
                    dto.setPhotos(workImageRepository.findAllByWorkRequestId(w.getId())
                            .stream().map(WorkImage::getPhotoUrl).collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
    }

    public void approve(UUID workRequestId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Doar un administrator poate aproba lucrări.");
        }

        workRequestRepository.findById(workRequestId).ifPresent(req -> {
            req.setStatus(WorkRequestStatus.APPROVED);
            workRequestRepository.save(req);
        });
    }

    public void reject(UUID workRequestId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Doar un administrator poate respinge lucrări.");
        }

        workRequestRepository.findById(workRequestId).ifPresent(req -> {
            req.setStatus(WorkRequestStatus.REJECTED);
            workRequestRepository.save(req);
        });
    }
}