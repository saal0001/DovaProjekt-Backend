package com.example.dovaprojektbackend.model;

import java.time.LocalDateTime;

import com.example.dovaprojektbackend.enums.BookingStatus;

import jakarta.persistence.*;

@Entity
public class Booking {

    @Enumerated(EnumType.STRING)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private int shopId;
    private int customerId;
    private BookingStatus status;
    private LocalDateTime createdAt;

    public Booking() {

    }

    public Booking(int bookingId, int shopId, int customerId, BookingStatus status, LocalDateTime createdAt) {
        this.bookingId = bookingId;
        this.shopId = shopId;
        this.customerId = customerId;
        this.status = status;
        this.createdAt = createdAt;
    }
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public int getShopId() {
        return shopId;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
    
}
