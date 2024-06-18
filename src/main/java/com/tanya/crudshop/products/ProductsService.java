package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductsService {
    ProductResponseDTO getProductById(UUID id);

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    List<SubscriberResponseDTO> getSubscribers(UUID id, int page, int pageSize);

    ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO);

    void deleteProduct(UUID id);
}
