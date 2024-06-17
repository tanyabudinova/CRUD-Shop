package com.tanya.crudshop.audits;

import com.tanya.crudshop.products.ProductResponseDTO;
import com.tanya.crudshop.products.ProductsRepository;
import com.tanya.crudshop.subscribers.SubscribersRepository;
import com.tanya.crudshop.utils.DateFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditsServiceImpl implements AuditsService {
    private final SubscribersRepository subscribersRepository;
    private final ProductsRepository productsRepository;

    public AuditsServiceImpl(SubscribersRepository subscribersRepository,
                             ProductsRepository productsRepository) {
        this.subscribersRepository = subscribersRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public Long numberOfSubscribers() {
        return subscribersRepository.count();
    }

    @Override
    public Long numberOfSoldProducts(LocalDate date, Boolean available) {
        return productsRepository.countSoldProductFilterByDateOrAvailable(date, available);
    }

    @Override
    public List<ProductResponseDTO> mostPopularProducts(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productsRepository.findProductsOrderedBySubscribersCount(pageable).stream()
                .map(product -> new ProductResponseDTO(product.getId(),
                        product.getName(), product.getAvailable(),
                        DateFormatter.format(product.getCreationDate())))
                .collect(Collectors.toList());
    }
}