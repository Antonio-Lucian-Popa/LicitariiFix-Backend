package com.asusoftware.LicitariiFix.work_request.repository;

import com.asusoftware.LicitariiFix.work_request.model.WorkRequest;
import com.asusoftware.LicitariiFix.work_request.model.WorkRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRequestRepository extends JpaRepository<WorkRequest, UUID> {

    List<WorkRequest> findAllByClientId(UUID clientId);

    List<WorkRequest> findAllByStatus(WorkRequestStatus status);
}