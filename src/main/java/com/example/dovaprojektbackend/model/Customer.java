package com.example.dovaprojektbackend.model;

import com.example.dovaprojektbackend.model.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
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

    @Column(nullable = false)
    private String name;

    @Column(unique = true,  nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private Long phone;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Customer(){}

    public Customer(String name, String email, Long phone, String address, Role role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}