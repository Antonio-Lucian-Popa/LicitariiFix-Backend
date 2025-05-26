package com.asusoftware.LicitariiFix.offer.repository;

import com.asusoftware.LicitariiFix.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    List<Offer> findAllByWorkRequestId(UUID workRequestId);

    List<Offer> findAllByMeseriasId(UUID meseriasId);

    boolean existsByWorkRequestIdAndMeseriasId(UUID workRequestId, UUID meseriasId);
}
