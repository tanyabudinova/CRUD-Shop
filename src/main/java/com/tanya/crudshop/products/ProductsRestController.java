package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberResponseDTO;
import com.tanya.crudshop.utils.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            ProductResponseDTO result = productsService.getProductById(id);
            return ResponseEntity.ok()
                    .body(result);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
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
        try {
            ProductResponseDTO result = productsService.updateProduct(id, productRequestDTO);
            return ResponseEntity.ok()
                    .body(result);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        try {
            productsService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
