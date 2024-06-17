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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditsServiceImplTests {
    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private AuditsServiceImpl auditsService;

    @Test
    void mostPopularProductsShouldReturn() {
        int page = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize);
        UUID id = UUID.randomUUID();
        String name = "Vafla";
        Boolean available = true;
        ProductEntity entity = new ProductEntity(name, LocalDate.MAX, available);
        entity.setId(id);
        ProductResponseDTO responseDTO = new ProductResponseDTO(id, name, available, LocalDate.MAX);

        when(productsRepository.findProductsOrderedBySubscribersCount(pageable))
                .thenReturn(List.of(entity));
        List<ProductResponseDTO> result = auditsService.mostPopularProducts(page, pageSize);

        Assertions.assertIterableEquals(List.of(responseDTO), result);
    }
}