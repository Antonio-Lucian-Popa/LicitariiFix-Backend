package com.asusoftware.LicitariiFix.offer.service;

import com.asusoftware.LicitariiFix.offer.model.Offer;
import com.asusoftware.LicitariiFix.offer.model.dto.CreateOfferDto;
import com.asusoftware.LicitariiFix.offer.model.dto.OfferDto;
import com.asusoftware.LicitariiFix.offer.repository.OfferRepository;
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
    private final ModelMapper mapper;

    public OfferDto create(UUID meseriasId, CreateOfferDto dto) {
        if (offerRepository.existsByWorkRequestIdAndMeseriasId(dto.getWorkRequestId(), meseriasId)) {
            throw new RuntimeException("Ai trimis deja o ofertă pentru această lucrare.");
        }

        Offer entity = Offer.builder()
                .price(dto.getPrice())
                .message(dto.getMessage())
                .meseriasId(meseriasId)
                .workRequestId(dto.getWorkRequestId())
                .build();
        offerRepository.save(entity);
        return mapper.map(entity, OfferDto.class);
    }

    public List<OfferDto> getAllByWork(UUID workRequestId) {
        return offerRepository.findAllByWorkRequestId(workRequestId)
                .stream().map(o -> mapper.map(o, OfferDto.class)).collect(Collectors.toList());
    }

    public List<OfferDto> getByMeserias(UUID meseriasId) {
        return offerRepository.findAllByMeseriasId(meseriasId)
                .stream().map(o -> mapper.map(o, OfferDto.class)).collect(Collectors.toList());
    }
}