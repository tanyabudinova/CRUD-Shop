package com.tanya.crudshop.subscribers;

import java.util.UUID;

public record SubscriberResponseDTO(UUID id, String firstName, String lastName, String joinedAt) {
}
