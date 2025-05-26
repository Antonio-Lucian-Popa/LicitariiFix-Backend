package com.asusoftware.LicitariiFix.notification.service;

import com.asusoftware.LicitariiFix.exception.UserNotFoundException;
import com.asusoftware.LicitariiFix.notification.model.Notification;
import com.asusoftware.LicitariiFix.notification.model.dto.CreateNotificationDto;
import com.asusoftware.LicitariiFix.notification.model.dto.NotificationDto;
import com.asusoftware.LicitariiFix.notification.repository.NotificationRepository;
import com.asusoftware.LicitariiFix.user.model.User;
import com.asusoftware.LicitariiFix.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public void send(CreateNotificationDto dto) {
        Notification n = Notification.builder()
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .build();
        notificationRepository.save(n);
    }

    public List<NotificationDto> getByUser(UUID keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new UserNotFoundException("Meserias not found"));

        return notificationRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(n -> mapper.map(n, NotificationDto.class)).collect(Collectors.toList());
    }

    public long getUnreadCount(UUID keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new UserNotFoundException("Meserias not found"));
        return notificationRepository.countByUserIdAndIsReadFalse(user.getId());
    }
}
