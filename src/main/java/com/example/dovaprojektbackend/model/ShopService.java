package com.example.dovaprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "services")
public class ShopService {

    @Id
    @Column(name = "service_id")
    private UUID shopServiceId;

    @PrePersist
    public void prePersist() {
        if (shopServiceId == null) {
            shopServiceId = UUID.randomUUID();
        }
    }

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 100)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonBackReference
    private Bikeshop bikeshop;

    @Transient  // Not saved to database
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Only for input
    private UUID shopId;

    public ShopService() {}

    public ShopService(UUID ShopServiceId, String name, BigDecimal price, String duration, String description, Bikeshop bikeshop) {
        this.shopServiceId = ShopServiceId;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.bikeshop = bikeshop;
    }

    public UUID getShopServiceId() {
        return shopServiceId;
    }

    public void setId(UUID ShopServiceId) {
        this.shopServiceId = ShopServiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Bikeshop getBikeshop() {
        return bikeshop;
    }

    public void setBikeshop(Bikeshop bikeshop) {
        this.bikeshop = bikeshop;
    }

    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
    }
}