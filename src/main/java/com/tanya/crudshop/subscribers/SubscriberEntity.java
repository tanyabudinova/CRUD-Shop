package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "subscribers")
public class SubscriberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDateTime joinedAt;
    @ManyToMany
    @JoinTable(
            name = "subscribers_products",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductEntity> products;
}
