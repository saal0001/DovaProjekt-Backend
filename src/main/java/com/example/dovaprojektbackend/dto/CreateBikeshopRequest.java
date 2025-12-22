package com.example.dovaprojektbackend.dto;

public class CreateBikeshopRequest {

    private String shopName;
    private String imageUrl;
    private Long phoneNumber;
    private String openingHours;
    private Integer cvrNumber;
    private String address;
    private String email;

    public CreateBikeshopRequest() {
    }

    public CreateBikeshopRequest(String shopName, String imageUrl, Long phoneNumber, String openingHours,
                                Integer cvrNumber, String address, String email) {
        this.shopName = shopName;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.cvrNumber = cvrNumber;
        this.address = address;
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

    public void setCvrNumber(Integer cvrNumber) {
        this.cvrNumber = cvrNumber;
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