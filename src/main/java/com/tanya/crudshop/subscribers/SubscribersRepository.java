package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubscribersRepository extends JpaRepository<SubscriberEntity, UUID> {
    List<SubscriberEntity> findByProductsId(UUID id, Pageable pageable);
}
