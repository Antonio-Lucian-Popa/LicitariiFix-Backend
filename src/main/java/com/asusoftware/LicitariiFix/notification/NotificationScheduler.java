package com.asusoftware.LicitariiFix.notification;

import com.asusoftware.LicitariiFix.notification.model.dto.CreateNotificationDto;
import com.asusoftware.LicitariiFix.notification.service.NotificationService;
import com.asusoftware.LicitariiFix.user.model.User;
import com.asusoftware.LicitariiFix.user.model.UserRole;
import com.asusoftware.LicitariiFix.user.repository.UserRepository;
import com.asusoftware.LicitariiFix.work_request.model.WorkRequest;
import com.asusoftware.LicitariiFix.work_request.model.WorkRequestStatus;
import com.asusoftware.LicitariiFix.work_request.repository.WorkRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final WorkRequestRepository workRequestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    // Zilnic la 09:00 dimineața – notifică adminii despre lucrările noi în așteptare
    @Scheduled(cron = "0 0 9 * * *")
    public void notifyAdminsAboutPendingWorkRequests() {
        List<WorkRequest> pendingRequests = workRequestRepository.findAllByStatus(WorkRequestStatus.PENDING);
        if (pendingRequests.isEmpty()) return;

        List<User> admins = userRepository.findAllByRole(UserRole.ADMIN);

        for (User admin : admins) {
            String message = String.format("%d lucrări sunt în așteptare pentru aprobare.", pendingRequests.size());
            notificationService.send(CreateNotificationDto.builder()
                    .userId(admin.getId())
                    .message(message)
                    .build());
            log.info("Notificare trimisă către admin {}: {}", admin.getEmail(), message);
        }
    }
}

