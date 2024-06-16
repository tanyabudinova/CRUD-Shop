package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberResponseDTO;
import com.tanya.crudshop.utils.DeletionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsRestController {
    private final ProductsService productsService;

    public ProductsRestController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable UUID id) {
        ProductResponseDTO result = productsService.getProductById(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO result = productsService.createProduct(productRequestDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/{id}/subscribers")
    public ResponseEntity<List<SubscriberResponseDTO>> getProductSubscribers(@PathVariable UUID id,
                                                                             @RequestParam Integer page,
                                                                             @RequestParam Integer pageSize) {
        List<SubscriberResponseDTO> result = productsService.getSubscribers(id, page, pageSize);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id,
                                                            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO result = productsService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletionResponse> deleteProduct(@PathVariable UUID id) {
        productsService.deleteProduct(id);
        return ResponseEntity.ok()
                .body(new DeletionResponse("Ok"));
    }
}
