package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductResponseDTO;
import com.tanya.crudshop.products.ProductsRepository;
import com.tanya.crudshop.utils.DateFormatter;
import com.tanya.crudshop.utils.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubscribersServiceImpl implements SubscribersService {
    private final SubscribersRepository subscribersRepository;
    private final ProductsRepository productsRepository;
    private final String errorNotFoundMessage = "Subscriber not found.";

    public SubscribersServiceImpl(SubscribersRepository subscribersRepository, ProductsRepository productsRepository) {
        this.subscribersRepository = subscribersRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public SubscriberResponseDTO getSubscriberById(UUID id) {
        return subscribersRepository.findById(id)
                .map(this::convertEntityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(errorNotFoundMessage, id));
    }

    @Override
    public SubscriberResponseDTO createSubscriber(SubscriberRequestDTO subscriberRequestDTO) {
        SubscriberEntity subscriber = new SubscriberEntity(subscriberRequestDTO.firstName(),
                subscriberRequestDTO.lastName(), LocalDateTime.now());
        SubscriberEntity savedSubscriber = subscribersRepository.save(subscriber);
        return convertEntityToDTO(savedSubscriber);
    }

    @Override
    public List<ProductResponseDTO> getProducts(UUID id, Integer page, Integer pageSize) {
        if (!subscribersRepository.existsById(id)) {
            throw new ResourceNotFoundException(errorNotFoundMessage, id);
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        return productsRepository.findBySubscribersId(id, pageable).stream()
                .map(product -> new ProductResponseDTO(product.getId(),
                        product.getName(), product.getAvailable(),
                        DateFormatter.format(product.getCreationDate())))
                .collect(Collectors.toList());
    }

    @Override
    public SubscriberResponseDTO updateSubscriber(UUID id, SubscriberRequestDTO subscriberRequestDTO) {
        SubscriberEntity subscriberEntity = subscribersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorNotFoundMessage, id));
        subscriberEntity.setFirstName(subscriberRequestDTO.firstName());
        subscriberEntity.setLastName(subscriberRequestDTO.lastName());
        SubscriberEntity savedSubscriber = subscribersRepository.save(subscriberEntity);
        return convertEntityToDTO(savedSubscriber);
    }

    @Override
    public void deleteSubscriber(UUID id) {
        if (subscribersRepository.existsById(id)) {
            subscribersRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(errorNotFoundMessage, id);
        }
    }

    private SubscriberResponseDTO convertEntityToDTO(SubscriberEntity entity) {
        return new SubscriberResponseDTO(entity.getId(),
                entity.getFirstName(), entity.getLastName(),
                DateFormatter.format(entity.getJoinedAt()));
    }
}
