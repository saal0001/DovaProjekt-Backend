package com.example.dovaprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    @Column(name = "image_url")
    private String imageUrl; //

    @Column(name = "phone_number",nullable = false,unique = true )
    private String phoneNumber;

    @Column(name = "opening_hours", nullable = false)
    private String openingHours;

    @Column(name = "cvr_number", nullable = false, unique = true, length = 8)
    @Pattern(regexp = "^\\d{8}$", message = "CVR skal være præcis 8 cifre")
    private String cvrNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikeshop")
    private List<ShopService> shopServices = new ArrayList<>();

    @OneToMany(mappedBy = "bikeshop")
    @JsonIgnoreProperties("bikeshop")
    private List<Booking> bookings = new ArrayList<>();

    public Bikeshop() {}

    @JsonProperty("shopId")
    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID id) {
        this.shopId = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getCvrNumber() {
        return cvrNumber;
    }

    public void setCvrNumber(String cvrNumber) {
        this.cvrNumber = cvrNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
