package com.tanya.crudshop.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(
        @NotBlank(message = "Name should not be empty.")
        String name,
        @NotNull
        Boolean available
) {
}
