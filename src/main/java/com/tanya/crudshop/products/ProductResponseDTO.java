package com.tanya.crudshop.products;

import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, Boolean available, String timestamp) {
}
