package com.tanya.crudshop.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
}
