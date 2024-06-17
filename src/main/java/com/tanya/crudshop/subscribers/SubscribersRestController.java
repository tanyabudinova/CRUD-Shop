package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductResponseDTO;
import com.tanya.crudshop.utils.DeletionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        SubscriberResponseDTO result = subscribersService.getSubscriberById(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping
    public ResponseEntity<SubscriberResponseDTO> createSubscriber(@RequestBody SubscriberRequestDTO subscriberRequestDTO) {
        SubscriberResponseDTO result = subscribersService.createSubscriber(subscriberRequestDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDTO>> getSubscriberProducts(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<ProductResponseDTO> result = subscribersService.getProducts(id, page, pageSize);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriberResponseDTO> updateSubscriber(
            @PathVariable UUID id,
            @Valid @RequestBody SubscriberRequestDTO subscriberRequestDTO) {
        SubscriberResponseDTO result = subscribersService.updateSubscriber(id, subscriberRequestDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletionResponse> deleteSubscriber(@PathVariable UUID id) {
        subscribersService.deleteSubscriber(id);
        return ResponseEntity.ok()
                .body(new DeletionResponse("Ok"));
    }

}
