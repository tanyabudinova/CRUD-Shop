package com.tanya.crudshop.audits;

import com.tanya.crudshop.products.ProductEntity;
import com.tanya.crudshop.products.ProductResponseDTO;
import com.tanya.crudshop.products.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditsServiceImplTests {
    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private AuditsServiceImpl auditsService;

    @Test
    void numberOfSoldProductsShouldThrowIllegalArgumentWhenBothAreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> auditsService.numberOfSoldProducts(null, null));
    }

    @Test
    void numberOfSoldProductsShouldWorkWhenOnlyDateIsNull() {
        assertDoesNotThrow(() -> auditsService.numberOfSoldProducts(null, true));
    }

    @Test
    void numberOfSoldProductsShouldWorkWhenOnlyAvailableIsNull() {
        assertDoesNotThrow(() -> auditsService.numberOfSoldProducts(LocalDate.now(), null));
    }

    @Test
    void mostPopularProductsShouldReturn() {
        int page = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize);
        UUID id = UUID.randomUUID();
        String name = "Vafla";
        boolean available = true;
        ProductEntity entity = new ProductEntity(name, available);
        entity.setId(id);
        ProductResponseDTO responseDTO = new ProductResponseDTO(id, name,
                available, entity.getCreationDate());

        when(productsRepository.findProductsOrderedBySubscribersCount(pageable))
                .thenReturn(List.of(entity));
        List<ProductResponseDTO> result = auditsService.mostPopularProducts(page, pageSize);

        Assertions.assertIterableEquals(List.of(responseDTO), result);
    }
}
