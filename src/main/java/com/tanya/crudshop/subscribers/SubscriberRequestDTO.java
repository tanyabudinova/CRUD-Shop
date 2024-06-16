package com.tanya.crudshop.subscribers;

import jakarta.validation.constraints.NotBlank;

public record SubscriberRequestDTO(
        @NotBlank(message = "First name should not be empty")
        String firstName,
        @NotBlank
        String lastName
) {
}
