package com.tanya.crudshop.products;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findBySubscribersId(UUID subscriberId, Pageable pageable);

    @Query(value = "SELECT p.* FROM products p " +
            "JOIN subscribers_products s ON p.id = s.product_id " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(s) DESC", nativeQuery = true)
    List<ProductEntity> findProductsOrderedBySubscribersCount(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT p.id FROM products p " +
            "JOIN subscribers_products s ON p.id = s.product_id " +
            "WHERE p.available = :available OR DATE(p.creation_date) = :date " +
            "GROUP BY p.id " +
            "HAVING COUNT(s) >= 1) r", nativeQuery = true)
    Long countSoldProductFilterByDateOrAvailable(@Param("date") LocalDate date,
                                                 @Param("available") Boolean available);
}
