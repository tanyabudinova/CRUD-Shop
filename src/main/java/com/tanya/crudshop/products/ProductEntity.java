package com.tanya.crudshop.products;

import com.tanya.crudshop.subscribers.SubscriberEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDate creationDate;
    private boolean available;
    @ManyToMany(mappedBy = "products")
    private List<SubscriberEntity> subscribers;

    public ProductEntity(String name, LocalDate creationDate, boolean available) {
        this.name = name;
        this.creationDate = creationDate;
        this.available = available;
    }

    public ProductEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<SubscriberEntity> getSubscribers() {
        return subscribers;
    }
}
