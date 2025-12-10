package com.example.dovaprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity

public class ShopService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private String duration;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "bikeShopFK", referencedColumnName = "bikeShopID")
    @JsonBackReference
    private Bikeshop bikeShop;

    public int getId() {
        return id;
    }

    public ShopService() {
    }

    public ShopService(int id, String name, double price, String duration, String description, Bikeshop bikeShop) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.bikeShop = bikeShop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bikeshop getShop() {
        return bikeShop;
    }

    public void setShop(Bikeshop bikeShop) {
        this.bikeShop = bikeShop;
    }

}
