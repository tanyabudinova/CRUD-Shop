package com.tanya.crudshop.subscribers;

import com.tanya.crudshop.products.ProductEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subscribers")
public class SubscriberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate joinedAt;
    @ManyToMany
    @JoinTable(
            name = "subscribers_products",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> products;

    public SubscriberEntity(String firstName, String lastName, LocalDate joinedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.joinedAt = joinedAt;
    }

    public SubscriberEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }
}
