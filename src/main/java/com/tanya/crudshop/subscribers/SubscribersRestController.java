package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductResponseDTO;
import com.tanya.crudshop.utils.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subscribers")
public class SubscribersRestController {
    private final SubscribersService subscribersService;

    SubscribersRestController(SubscribersService subscribersService) {
        this.subscribersService = subscribersService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberResponseDTO> getSubscriber(@PathVariable UUID id) {
        try {
            SubscriberResponseDTO result = subscribersService.getSubscriberById(id);
            return ResponseEntity.ok()
                    .body(result);
        } catch(ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping
    public ResponseEntity<SubscriberResponseDTO> createSubscriber(@RequestBody SubscriberRequestDTO subscriberRequestDTO) {
        SubscriberResponseDTO result = subscribersService.createSubscriber(subscriberRequestDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDTO>> getSubscriberProducts(@PathVariable UUID id,
                                                                          @RequestParam Integer page,
                                                                          @RequestParam Integer pageSize) {
        List<ProductResponseDTO> result = subscribersService.getProducts(id, page, pageSize);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriberResponseDTO> updateSubscriber(@PathVariable UUID id,
                                              @Valid @RequestBody SubscriberRequestDTO subscriberRequestDTO) {
        try {
            SubscriberResponseDTO result = subscribersService.updateSubscriber(id, subscriberRequestDTO);
            return ResponseEntity.ok()
                    .body(result);
        } catch(ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscriber(@PathVariable UUID id) {
        try {
            subscribersService.deleteSubscriber(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

}
