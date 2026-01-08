package com.example.dovaprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @PrePersist
    public void prePersist() {
        if (customerId == null) {
            customerId = UUID.randomUUID();
        }
    }

    @NotBlank(message = "Navn er påkrævet")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email er påkrævet")
    @Email(message = "Email skal være gyldig")
    @Column(unique = true,  nullable = false)
    private String email;

    @NotBlank(message = "Telefonnummer er påkrævet")
    @Column(unique = true, nullable = false)
    private String phone;

    @NotBlank(message = "Adresse er påkrævet")
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<Booking> bookings = new ArrayList<>();

    public Customer(){}

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID id) {
        this.customerId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}