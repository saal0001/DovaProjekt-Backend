package com.example.dovaprojektbackend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.dovaprojektbackend.model.enums.BookingStatus;

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
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private BookingStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Booking() {

    }

    public Booking(User user, BookingStatus status, LocalDateTime createdAt) {
        this.user = user;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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