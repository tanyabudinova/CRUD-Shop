package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductsService {
    List<ProductResponseDTO> getProductByName(String name);
    ProductResponseDTO getProductById(UUID id);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    List<SubscriberResponseDTO> getSubscribers(UUID id, Integer page, Integer pageSize);
    ProductEntity updateProduct(ProductRequestDTO productRequestDTO);
    void deleteProduct(UUID id);
}
