package com.tanya.crudshop.subscribers;

import java.time.LocalDate;
import java.util.UUID;

public record SubscriberResponseDTO(UUID id, String firstName, String lastName, LocalDate joinedAt) {
}
