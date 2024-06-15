package com.tanya.crudshop.subscribers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscribersRepository extends JpaRepository<SubscriberEntity, UUID> {
}
