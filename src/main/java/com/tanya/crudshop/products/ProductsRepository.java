package com.tanya.crudshop.products;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findBySubscribersId(UUID subscriberId, Pageable pageable);
}
