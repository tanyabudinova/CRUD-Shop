package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberEntity;
import com.tanya.crudshop.subscribers.SubscriberResponseDTO;
import com.tanya.crudshop.subscribers.SubscribersRepository;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceImplTests {
    @Mock
    ProductsRepository productsRepository;

    @Mock
    SubscribersRepository subscribersRepository;

    @InjectMocks
    ProductsServiceImpl productsService;

    @Test
    void getProductByIdShouldGetProduct() {
        String name = "Random";
        boolean available = true;
        LocalDate date = LocalDate.of(2024, 6, 17);
        ProductEntity entity = new ProductEntity(name, date, available);
        UUID id = UUID.randomUUID();
        entity.setId(id);
        Optional<ProductEntity> opEntity = Optional.of(entity);
        ProductResponseDTO dtoExpected = new ProductResponseDTO(id, name, available, date);

        when(productsRepository.findById(id)).thenReturn(opEntity);
        ProductResponseDTO result = productsService.getProductById(id);

        assertEquals(dtoExpected, result);
    }

    @Test
    void getProductByIdShouldThrowNotFoundExceptionWhenMissingProduct() {
        UUID id = UUID.randomUUID();
        Optional<ProductEntity> opEntity = Optional.empty();

        when(productsRepository.findById(id)).thenReturn(opEntity);

        assertThrows(ResourceNotFoundException.class, () -> productsService.getProductById(id));
    }

    @Test
    void createProductShouldCreate() {
        String name = "Random";
        boolean available = false;
        ProductEntity entity = new ProductEntity(name, LocalDate.now(), available);
        LocalDate date = LocalDate.of(2024, 6, 17);
        ProductEntity savedEntity = new ProductEntity(name, date, available);
        UUID id = UUID.randomUUID();
        savedEntity.setId(id);
        ProductRequestDTO requestDTO = new ProductRequestDTO(name, available);
        ProductResponseDTO dtoExpected = new ProductResponseDTO(id, name, available, date);

        when(productsRepository.save(argThat(new ProductsServiceImplTests.ProductEntityMatcher(entity)))).thenReturn(savedEntity);
        ProductResponseDTO result = productsService.createProduct(requestDTO);

        assertEquals(dtoExpected, result);
    }

    @Test
    void getSubscribersShouldThrowNotFoundExceptionWhenMissingEntity() {
        UUID id = UUID.randomUUID();
        int page = 0;
        int pageSize = 5;

        when(productsRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> productsService.getSubscribers(id, page, pageSize));
    }

    @Test
    void getSubscribersShouldReturnCorrectNumberOfEntities() {
        UUID argumentId = UUID.randomUUID();
        UUID subscriber1Id = UUID.randomUUID();
        UUID subscriber2Id = UUID.randomUUID();
        String subscriber1FirstName = "John";
        String subscriber1LastName = "Smith";
        String subscriber2firstName = "Dona";
        String subscriber2LastName = "Noble";
        LocalDate date1 = LocalDate.of(2024, 6, 17);
        LocalDate date2 = LocalDate.of(2024, 12, 3);
        SubscriberEntity subscriberEntity1 = new SubscriberEntity(subscriber1FirstName,
                subscriber1LastName, date1);
        subscriberEntity1.setId(subscriber1Id);
        SubscriberEntity subscriberEntity2 = new SubscriberEntity(subscriber2firstName,
                subscriber2LastName, date2);
        subscriberEntity2.setId(subscriber2Id);
        SubscriberResponseDTO subscriberDTO1 = new SubscriberResponseDTO(subscriber1Id, subscriber1FirstName,
                subscriber1LastName, date1);
        subscriberEntity1.setId(subscriber1Id);
        SubscriberResponseDTO subscriberDTO2 = new SubscriberResponseDTO(subscriber2Id, subscriber2firstName,
                subscriber2LastName, date2);
        subscriberEntity2.setId(subscriber2Id);
        int page = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize);

        when(productsRepository.existsById(argumentId)).thenReturn(true);
        when(subscribersRepository.findByProductsId(argumentId, pageable))
                .thenReturn(List.of(subscriberEntity1, subscriberEntity2));
        List<SubscriberResponseDTO> result = productsService.getSubscribers(argumentId, page, pageSize);

        Assertions.assertIterableEquals(List.of(subscriberDTO1, subscriberDTO2), result);
    }

    @Test
    void getSubscribersShouldReturnEmptyListWhenNoProducts() {
        UUID argumentId = UUID.randomUUID();
        int page = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize);

        when(productsRepository.existsById(argumentId)).thenReturn(true);
        when(subscribersRepository.findByProductsId(argumentId, pageable))
                .thenReturn(Collections.emptyList());
        List<SubscriberResponseDTO> result = productsService.getSubscribers(argumentId, page, pageSize);

        Assertions.assertIterableEquals(Collections.emptyList(), result);
    }

    @Test
    void updateProductShouldThrowNotFoundExceptionWhenMissingEntity() {
        UUID id = UUID.randomUUID();
        ProductRequestDTO requestDTO = new ProductRequestDTO("Random", true);

        when(productsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productsService.updateProduct(id, requestDTO));
    }

    @Test
    void updateProductShouldReturnUpdatedEntity() {
        UUID id = UUID.randomUUID();
        String name = "Random";
        boolean available = false;
        LocalDate date = LocalDate.of(2023, 7, 5);
        ProductRequestDTO requestDTO = new ProductRequestDTO(name, available);
        ProductEntity productEntity = new ProductEntity(name, date, available);
        productEntity.setId(id);
        ProductEntity preUpdateEntity = new ProductEntity("Pesho", date, available);
        preUpdateEntity.setId(id);
        ProductResponseDTO responseDTO = new ProductResponseDTO(id, name, available, date);

        when(productsRepository.findById(id)).thenReturn(Optional.of(preUpdateEntity));
        when(productsRepository.save(argThat(new ProductsServiceImplTests.ProductEntityMatcher(productEntity))))
                .thenReturn(productEntity);
        ProductResponseDTO result = productsService.updateProduct(id, requestDTO);

        assertEquals(responseDTO, result);
    }

    @Test
    void deleteProductShouldThrowNotFoundExceptionWhenMissingEntity() {
        UUID id = UUID.randomUUID();

        when(productsRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productsService.deleteProduct(id));
    }

    @Test
    void deleteProductShouldNotThrowWhenDeletingEntity() {
        UUID id = UUID.randomUUID();

        when(productsRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> productsService.deleteProduct(id));
        verify(productsRepository, times(1)).deleteById(id);
    }

    private static class ProductEntityMatcher implements ArgumentMatcher<ProductEntity> {
        private final ProductEntity left;

        ProductEntityMatcher(ProductEntity entity) {
            left = entity;
        }

        @Override
        public boolean matches(ProductEntity right) {
            return left.getName().equals(right.getName()) &&
                    left.getAvailable() == right.getAvailable() &&
                    (left.getCreationDate().isBefore(right.getCreationDate()) ||
                            left.getCreationDate().isEqual(right.getCreationDate()));
        }
    }
}
