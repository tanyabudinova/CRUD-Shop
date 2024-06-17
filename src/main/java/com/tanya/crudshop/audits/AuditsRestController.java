package com.tanya.crudshop.audits;

import com.tanya.crudshop.products.ProductResponseDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/audits")
public class AuditsRestController {
    private final AuditsService auditsService;

    public AuditsRestController(AuditsService auditsService) {
        this.auditsService = auditsService;
    }

    @GetMapping("/subscribers")
    public ResponseEntity<Map<String, Long>> numberOfSubscribers() {
        Long count = auditsService.numberOfSubscribers();
        return ResponseEntity.ok()
                .body(Map.of("count", count));
    }

    @GetMapping("/products/sold")
    public ResponseEntity<Map<String, Long>> numberOfSoldProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Boolean available) {
        Long count = auditsService.numberOfSoldProducts(date, available);
        return ResponseEntity.ok()
                .body(Map.of("count", count));
    }

    @GetMapping("/products/popular")
    public ResponseEntity<List<ProductResponseDTO>> mostPopularProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<ProductResponseDTO> result = auditsService.mostPopularProducts(page, pageSize);
        return ResponseEntity.ok()
                .body(result);
    }
 }
