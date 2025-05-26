package com.asusoftware.LicitariiFix.work_request.repository;

import com.asusoftware.LicitariiFix.work_request.model.WorkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkImageRepository extends JpaRepository<WorkImage, UUID> {

    List<WorkImage> findAllByWorkRequestId(UUID workRequestId);
}
