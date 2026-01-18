package com.example.dovaprojektbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateBikeshopRequest {

    @NotBlank(message = "Shop navn er påkrævet")
    private String shopName;

    private String imageUrl; // Optional felt

    @NotBlank(message = "Telefonnummer er påkrævet")
    private String phoneNumber;

    @NotBlank(message = "Åbningstider er påkrævet")
    private String openingHours;

    @NotBlank(message = "CVR nummer er påkrævet")
    @Pattern(regexp = "^\\d{8}$", message = "CVR skal være præcis 8 cifre")
    private String cvrNumber;

    @NotBlank(message = "Adresse er påkrævet")
    private String address;

    @NotBlank(message = "By er påkrævet")
    private String city;

    @NotBlank(message = "Email er påkrævet")
    @Email(message = "Email skal være gyldig")
    private String email;

    public CreateBikeshopRequest() {
    }

    public CreateBikeshopRequest(String shopName, String imageUrl, String phoneNumber, String openingHours,
                                String cvrNumber, String address, String city, String email) {
        this.shopName = shopName;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.cvrNumber = cvrNumber;
        this.address = address;
        this.city = city;
        this.email = email;
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
}