package com.example.dovaprojektbackend.dto.request;

import java.util.UUID;

public class SignupShopRequest {
    private UUID userId;
    private String shopName;
    private String phoneNumber;
    private String openingHours;
    private Integer crNumber;
    private String address;
    private String email;

    public SignupShopRequest() {}

    public SignupShopRequest(String shopName, String phoneNumber, String openingHours, Integer crNumber, String address, String email) {
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.crNumber = crNumber;
        this.address = address;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

}
