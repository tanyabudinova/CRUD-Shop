package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductEntity;
import com.tanya.crudshop.products.ProductResponseDTO;
import com.tanya.crudshop.products.ProductsRepository;
import com.tanya.crudshop.utils.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscribersServiceImplTests {
    @Mock
    SubscribersRepository subscribersRepository;

    @Mock
    ProductsRepository productsRepository;

    @InjectMocks
    SubscribersServiceImpl subscribersService;

    @Test
    void getSubscriberByIdShouldGetSubscriber() {
        String firstName = "Random";
        String lastName = "Randomov";
        SubscriberEntity entity = new SubscriberEntity(firstName, lastName);
        UUID id = UUID.randomUUID();
        entity.setId(id);
        Optional<SubscriberEntity> opEntity = Optional.of(entity);
        SubscriberResponseDTO dtoExpected = new SubscriberResponseDTO(id, firstName,
                lastName, entity.getJoinedAt());

        when(subscribersRepository.findById(id)).thenReturn(opEntity);
        SubscriberResponseDTO result = subscribersService.getSubscriberById(id);

        assertEquals(dtoExpected, result);
    }

    @Test
    void getSubscriberByIdShouldThrowNotFoundExceptionWhenMissingSubscriber() {
        UUID id = UUID.randomUUID();
        Optional<SubscriberEntity> opEntity = Optional.empty();

        when(subscribersRepository.findById(id)).thenReturn(opEntity);

        assertThrows(ResourceNotFoundException.class, () -> subscribersService.getSubscriberById(id));
    }

    @Test
    void createSubscriberShouldCreate() {
        String firstName = "Random";
        String lastName = "Randomov";
        SubscriberEntity entity = new SubscriberEntity(firstName, lastName);
        SubscriberEntity savedEntity = new SubscriberEntity(firstName, lastName);
        UUID id = UUID.randomUUID();
        savedEntity.setId(id);
        SubscriberRequestDTO requestDTO = new SubscriberRequestDTO(firstName, lastName);
        SubscriberResponseDTO dtoExpected = new SubscriberResponseDTO(id, firstName,
                lastName, savedEntity.getJoinedAt());

        when(subscribersRepository.save(argThat(new SubscriberEntityMatcher(entity)))).thenReturn(savedEntity);
        SubscriberResponseDTO result = subscribersService.createSubscriber(requestDTO);

        assertEquals(dtoExpected, result);
    }

    @Test
    void getProductsShouldThrowNotFoundExceptionWhenMissingSubscriber() {
        UUID id = UUID.randomUUID();
        int page = 0;
        int pageSize = 5;

        when(subscribersRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> subscribersService.getProducts(id, page, pageSize));
    }

    @Test
    void getProductsShouldReturnCorrectNumberOfEntities() {
        UUID argumentId = UUID.randomUUID();
        UUID product1Id = UUID.randomUUID();
        UUID product2Id = UUID.randomUUID();
        String product1Name = "Vafla";
        String product2Name = "Djvachka";
        ProductEntity productEntity1 = new ProductEntity(product1Name, true);
        productEntity1.setId(product1Id);
        ProductEntity productEntity2 = new ProductEntity(product2Name, false);
        productEntity2.setId(product2Id);
        ProductResponseDTO productDTO1 = new ProductResponseDTO(product1Id, product1Name,
                true, productEntity1.getCreationDate());
        productEntity1.setId(product1Id);
        ProductResponseDTO productDTO2 = new ProductResponseDTO(product2Id, product2Name,
                false, productEntity2.getCreationDate());
        productEntity2.setId(product2Id);
        int page = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize);

        when(subscribersRepository.existsById(argumentId)).thenReturn(true);
        when(productsRepository.findBySubscribersId(argumentId, pageable))
                .thenReturn(List.of(productEntity1, productEntity2));
        List<ProductResponseDTO> result = subscribersService.getProducts(argumentId, page, pageSize);

        Assertions.assertIterableEquals(List.of(productDTO1, productDTO2), result);
    }

    @Test
    void getProductsShouldReturnEmptyListWhenNoProducts() {
        UUID argumentId = UUID.randomUUID();
        int page = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize);

        when(subscribersRepository.existsById(argumentId)).thenReturn(true);
        when(productsRepository.findBySubscribersId(argumentId, pageable))
                .thenReturn(Collections.emptyList());
        List<ProductResponseDTO> result = subscribersService.getProducts(argumentId, page, pageSize);

        Assertions.assertIterableEquals(Collections.emptyList(), result);
    }

    @Test
    void updateSubscriberShouldThrowNotFoundExceptionWhenMissingSubscriber() {
        UUID id = UUID.randomUUID();
        SubscriberRequestDTO requestDTO = new SubscriberRequestDTO("Random", "Randomov");

        when(subscribersRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> subscribersService.updateSubscriber(id, requestDTO));
    }

    @Test
    void updateSubscriberShouldReturnUpdatedEntity() {
        UUID id = UUID.randomUUID();
        String firstName = "Random";
        String lastName = "Randomov";
        SubscriberRequestDTO requestDTO = new SubscriberRequestDTO(firstName, lastName);
        SubscriberEntity subscriberEntity = new SubscriberEntity(firstName, lastName);
        subscriberEntity.setId(id);
        SubscriberEntity preUpdateEntity = new SubscriberEntity("Pesho", lastName);
        preUpdateEntity.setId(id);
        SubscriberResponseDTO responseDTO = new SubscriberResponseDTO(id, firstName,
                lastName, subscriberEntity.getJoinedAt());

        when(subscribersRepository.findById(id)).thenReturn(Optional.of(preUpdateEntity));
        when(subscribersRepository.save(argThat(new SubscriberEntityMatcher(subscriberEntity))))
                .thenReturn(subscriberEntity);
        SubscriberResponseDTO result = subscribersService.updateSubscriber(id, requestDTO);

        assertEquals(responseDTO, result);
    }

    @Test
    void deleteSubscriberShouldThrowNotFoundExceptionWhenMissingSubscriber() {
        UUID id = UUID.randomUUID();

        when(subscribersRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> subscribersService.deleteSubscriber(id));
    }

    @Test
    void deleteSubscriberShouldNotThrowWhenDeletingEntity() {
        UUID id = UUID.randomUUID();

        when(subscribersRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> subscribersService.deleteSubscriber(id));
        verify(subscribersRepository, times(1)).deleteById(id);
    }

    private static class SubscriberEntityMatcher implements ArgumentMatcher<SubscriberEntity> {
        private final SubscriberEntity left;

        SubscriberEntityMatcher(SubscriberEntity entity) {
            left = entity;
        }

        @Override
        public boolean matches(SubscriberEntity right) {
            return left.getFirstName().equals(right.getFirstName()) &&
                    left.getLastName().equals(right.getLastName()) &&
                    (left.getJoinedAt().isBefore(right.getJoinedAt()) ||
                            left.getJoinedAt().isEqual(right.getJoinedAt()));
        }
    }
}
