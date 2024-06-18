package com.tanya.crudshop.audits;

import com.tanya.crudshop.products.ProductResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AuditsService {
    Long numberOfSubscribers();

    Long numberOfSoldProducts(LocalDate date, Boolean available);

    List<ProductResponseDTO> mostPopularProducts(int page, int pageSize);
}
