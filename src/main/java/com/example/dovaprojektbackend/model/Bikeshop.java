package com.example.dovaprojektbackend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "workshops")
public class Bikeshop {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String openingHours;

    @Column(nullable = false)
    private Integer crNumber;

    @Column(nullable = false)
    private String address;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @Column(nullable = false)
    private Boolean isActive = false;

    public Bikeshop() {}

    public Bikeshop(String shopName, String phoneNumber, String openingHours, Integer crNumber, String address, String email) {
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.crNumber = crNumber;
        this.address = address;
        this.email = email;
        this.isVerified = false;
        this.isActive = false;
    }

    public UUID id() {
        return id;
    }

    public void id(UUID id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Integer getCrNumber() {
        return crNumber;
    }

    public void setCrNumber(Integer crNumber) {
        this.crNumber = crNumber;
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

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
