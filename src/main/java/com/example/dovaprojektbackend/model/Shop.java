package com.example.dovaprojektbackend.model;

import java.util.Collection;
import jakarta.persistence.*;

@Entity
public class Shop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shopId;
    private String shopNavn;
    private int phoneNumber;
    private String openingHours;
    private int cvrNumber;
    private String address;
    private String password;
    private String email;

    public Shop() {
    }

    public Shop(int shopId, String shopNavn, int phoneNumber, String openingHours, int cvrNumber, String address,
            String password, String email, Collection<Ydelse> ydelses) {
        this.shopId = shopId;
        this.shopNavn = shopNavn;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.cvrNumber = cvrNumber;
        this.address = address;
        this.password = password;
        this.email = email;
        this.ydelses = ydelses;
    }

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "shop")
    private Collection<Ydelse> ydelses;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopNavn() {
        return shopNavn;
    }

    public void setShopNavn(String shopNavn) {
        this.shopNavn = shopNavn;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public int getCvrNumber() {
        return cvrNumber;
    }

    public void setCvrNumber(int cvrNumber) {
        this.cvrNumber = cvrNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Ydelse> getYdelses() {
        return ydelses;
    }

    public void setYdelses(Collection<Ydelse> ydelses) {
        this.ydelses = ydelses;
    }    

}
