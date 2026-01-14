package com.example.dovaprojektbackend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.dovaprojektbackend.model.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_id")
    private UUID bookingId;

    @PrePersist
    public void prePersist() {
        if (bookingId == null) {
            bookingId = UUID.randomUUID();
        }
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties("bookings")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonIgnoreProperties("bookings")
    private Bikeshop bikeshop;

    @ManyToOne
    @JoinColumn(name = "shop_service_id", nullable = false)
    private ShopService shopService;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private BookingStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Booking() {

    }

    public Booking(Customer customer,Bikeshop bikeshop, ShopService shopService, BookingStatus status, LocalDateTime createdAt) {
        this.customer = customer;
        this.bikeshop = bikeshop;
        this.shopService = shopService;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bikeshop getBikeshop() {
        return bikeshop;
    }

    public void setBikeshop(Bikeshop bikeshop) {
        this.bikeshop = bikeshop;
    }

    public ShopService getShopService() {
        return shopService;
    }

    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("userId")
    public UUID getUserId() {
        return customer != null ? customer.getCustomerId() : null;
    }

}