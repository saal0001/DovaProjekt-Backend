package com.example.dovaprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @NotBlank(message = "Service navn er påkrævet")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Pris er påkrævet")
    @Positive(message = "Pris skal være positiv")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 100)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonBackReference
    private Bikeshop bikeshop;

    @Transient  // Not saved to database
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Only for input
    private UUID shopId;

    @OneToMany(mappedBy = "shopService")
    @JsonIgnoreProperties("shopService")
    private List<Booking> bookings = new ArrayList<>();

    public ShopService() {}

    public UUID getShopServiceId() {
        return shopServiceId;
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}