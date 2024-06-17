package com.tanya.crudshop.utils;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    private final UUID resourceId;

    public ResourceNotFoundException(String message, UUID resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public UUID getResourceId() {
        return resourceId;
    }
}
