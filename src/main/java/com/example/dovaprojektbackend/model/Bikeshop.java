package com.example.dovaprojektbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "bikeshop")
public class Bikeshop {
    @Id
    @Column(name = "shop_id")
    private UUID shopId;

    @PrePersist
    public void prePersist() {
        if (shopId == null) {
            shopId = UUID.randomUUID();
        }
    }

    @Column(name = "shop_name",nullable = false)
    private String shopName;

    @Column(name = "phone_number",nullable = false)
    private Long phoneNumber;

    @Column(name = "opening_hours", nullable = false)
    private String openingHours;

    @Column(name = "cvr_number",nullable = false, unique = true)
    private Integer cvrNumber;

    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "bikeshop")
    private List<ShopService> shopServices = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Bikeshop() {}

    public Bikeshop(String shopName, Long phoneNumber, String openingHours, Integer cvrNumber, String address, String email) {
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.cvrNumber = cvrNumber;
        this.address = address;
        this.email = email;
    }

    public UUID getId() {
        return shopId;
    }

    public void setId(UUID id) {
        this.shopId = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Integer getCvrNumber() {
        return cvrNumber;
    }

    public void setCvrNumber(Integer crNumber) {
        this.cvrNumber = crNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
