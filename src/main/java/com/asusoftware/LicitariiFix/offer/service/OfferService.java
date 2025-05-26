package com.asusoftware.LicitariiFix.offer.service;

import com.asusoftware.LicitariiFix.exception.UserNotFoundException;
import com.asusoftware.LicitariiFix.notification.model.dto.CreateNotificationDto;
import com.asusoftware.LicitariiFix.notification.service.NotificationService;
import com.asusoftware.LicitariiFix.offer.model.Offer;
import com.asusoftware.LicitariiFix.offer.model.dto.CreateOfferDto;
import com.asusoftware.LicitariiFix.offer.model.dto.OfferDto;
import com.asusoftware.LicitariiFix.offer.repository.OfferRepository;
import com.asusoftware.LicitariiFix.user.model.User;
import com.asusoftware.LicitariiFix.user.repository.UserRepository;
import com.asusoftware.LicitariiFix.work_request.repository.WorkRequestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final WorkRequestRepository workRequestRepository;
    private final NotificationService notificationService;
    private final ModelMapper mapper;

    public OfferDto create(UUID keycloakId, CreateOfferDto dto) {
        User meserias = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new UserNotFoundException("Meserias not found"));
        if (offerRepository.existsByWorkRequestIdAndMeseriasId(dto.getWorkRequestId(), meserias.getId())) {
            throw new RuntimeException("Ai trimis deja o ofertă pentru această lucrare.");
        }

        Offer entity = Offer.builder()
                .price(dto.getPrice())
                .message(dto.getMessage())
                .meseriasId(meserias.getId())
                .workRequestId(dto.getWorkRequestId())
                .build();
        offerRepository.save(entity);

        // Notifică clientul că a primit o ofertă nouă
        workRequestRepository.findById(dto.getWorkRequestId()).ifPresent(req -> {
            notificationService.send(CreateNotificationDto.builder()
                    .userId(req.getClientId())
                    .message("Ai primit o ofertă nouă pentru lucrarea \"" + req.getTitle() + "\".")
                    .build());
        });
        return mapper.map(entity, OfferDto.class);
    }

    public List<OfferDto> getAllByWork(UUID workRequestId) {
        return offerRepository.findAllByWorkRequestId(workRequestId)
                .stream().map(o -> mapper.map(o, OfferDto.class)).collect(Collectors.toList());
    }

    public List<OfferDto> getByMeserias(UUID keycloakId) {
        User meserias = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new UserNotFoundException("Meserias not found"));
        return offerRepository.findAllByMeseriasId(meserias.getId())
                .stream().map(o -> mapper.map(o, OfferDto.class)).collect(Collectors.toList());
    }
}