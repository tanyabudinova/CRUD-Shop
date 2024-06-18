package com.tanya.crudshop.audits;

import com.tanya.crudshop.products.ProductResponseDTO;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface AuditsService {
    Long numberOfSubscribers();

    Long numberOfSoldProducts(@Nullable LocalDate date, @Nullable Boolean available);

    List<ProductResponseDTO> mostPopularProducts(int page, int pageSize);
}
