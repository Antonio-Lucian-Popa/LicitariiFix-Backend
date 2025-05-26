package com.asusoftware.LicitariiFix.work_request.service;

import com.asusoftware.LicitariiFix.work_request.model.WorkRequest;
import com.asusoftware.LicitariiFix.work_request.model.WorkRequestStatus;
import com.asusoftware.LicitariiFix.work_request.model.dto.CreateWorkRequestDto;
import com.asusoftware.LicitariiFix.work_request.model.dto.WorkRequestDto;
import com.asusoftware.LicitariiFix.work_request.repository.WorkRequestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkRequestService {

    private final WorkRequestRepository workRequestRepository;
    private final ModelMapper mapper;

    public WorkRequestDto create(UUID clientId, CreateWorkRequestDto dto) {
        WorkRequest entity = WorkRequest.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .photos(dto.getPhotos())
                .status(WorkRequestStatus.PENDING)
                .clientId(clientId)
                .build();
        workRequestRepository.save(entity);
        return mapper.map(entity, WorkRequestDto.class);
    }

    public List<WorkRequestDto> getAllApproved() {
        return workRequestRepository.findAllByStatus(WorkRequestStatus.APPROVED)
                .stream().map(w -> mapper.map(w, WorkRequestDto.class)).collect(Collectors.toList());
    }

    public List<WorkRequestDto> getByClient(UUID clientId) {
        return workRequestRepository.findAllByClientId(clientId)
                .stream().map(w -> mapper.map(w, WorkRequestDto.class)).collect(Collectors.toList());
    }

    public void approve(UUID workRequestId) {
        workRequestRepository.findById(workRequestId).ifPresent(req -> {
            req.setStatus(WorkRequestStatus.APPROVED);
            workRequestRepository.save(req);
        });
    }

    public void reject(UUID workRequestId) {
        workRequestRepository.findById(workRequestId).ifPresent(req -> {
            req.setStatus(WorkRequestStatus.REJECTED);
            workRequestRepository.save(req);
        });
    }
}