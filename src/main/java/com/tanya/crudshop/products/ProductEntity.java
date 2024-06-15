package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDateTime timestamp;
    private Boolean available;
    @ManyToMany(mappedBy = "products")
    private List<SubscriberEntity> subscribers;
}
