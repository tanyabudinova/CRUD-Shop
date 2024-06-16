package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberResponseDTO;
import com.tanya.crudshop.subscribers.SubscribersRepository;
import com.tanya.crudshop.utils.DateFormatter;
import com.tanya.crudshop.utils.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final SubscribersRepository subscribersRepository;
    private final String errorNotFoundMessage = "Product noy found.";

    public ProductsServiceImpl(ProductsRepository productsRepository, SubscribersRepository subscribersRepository) {
        this.productsRepository = productsRepository;
        this.subscribersRepository = subscribersRepository;
    }

    @Override
    public ProductResponseDTO getProductById(UUID id) {
        return productsRepository.findById(id)
                .map(this::convertEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException(errorNotFoundMessage));
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = new ProductEntity(productRequestDTO.name(),
                LocalDateTime.now(), productRequestDTO.available());
        ProductEntity savedProductEntity = productsRepository.save(productEntity);
        return convertEntityToDTO(savedProductEntity);
    }

    @Override
    public List<SubscriberResponseDTO> getSubscribers(UUID id, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return subscribersRepository.findByProductsId(id, pageable).stream()
                .map(subscriberEntity -> new SubscriberResponseDTO(subscriberEntity.getId(),
                        subscriberEntity.getFirstName(), subscriberEntity.getLastName(),
                        DateFormatter.format(subscriberEntity.getJoinedAt())))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorNotFoundMessage));
        productEntity.setName(productRequestDTO.name());
        productEntity.setAvailable(productRequestDTO.available());
        ProductEntity savedProductEntity = productsRepository.save(productEntity);
        return convertEntityToDTO(savedProductEntity);
    }

    @Override
    public void deleteProduct(UUID id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(errorNotFoundMessage);
        }
    }

    private ProductResponseDTO convertEntityToDTO(ProductEntity entity) {
        return new ProductResponseDTO(entity.getId(),
                entity.getName(), entity.getAvailable(),
                DateFormatter.format(entity.getCreationDate()));
    }
}
