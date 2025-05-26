package com.asusoftware.LicitariiFix.notification.service;

import com.asusoftware.LicitariiFix.notification.model.Notification;
import com.asusoftware.LicitariiFix.notification.model.dto.CreateNotificationDto;
import com.asusoftware.LicitariiFix.notification.model.dto.NotificationDto;
import com.asusoftware.LicitariiFix.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper mapper;

    public void send(CreateNotificationDto dto) {
        Notification n = Notification.builder()
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .build();
        notificationRepository.save(n);
    }

    public List<NotificationDto> getByUser(UUID userId) {
        return notificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(n -> mapper.map(n, NotificationDto.class)).collect(Collectors.toList());
    }

    public long getUnreadCount(UUID userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
