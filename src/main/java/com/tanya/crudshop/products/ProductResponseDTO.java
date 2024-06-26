package com.tanya.crudshop.products;

import java.time.LocalDate;
import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, boolean available, LocalDate timestamp) {
}
