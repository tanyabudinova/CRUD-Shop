package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SubscribersService {
    SubscriberResponseDTO getSubscriberById(UUID id);

    SubscriberResponseDTO createSubscriber(SubscriberRequestDTO subscriberRequestDTO);

    List<ProductResponseDTO> getProducts(UUID id, Integer page, Integer pageSize);

    SubscriberResponseDTO updateSubscriber(UUID id, SubscriberRequestDTO subscriberRequestDTO);

    void deleteSubscriber(UUID id);
}
