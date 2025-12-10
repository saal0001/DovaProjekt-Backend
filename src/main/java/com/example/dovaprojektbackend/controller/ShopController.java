package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.model.Bikeshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dovaprojektbackend.service.BikeShopService;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    BikeShopService bikeShopService;

    @PostMapping("/create")
    public Bikeshop createShop(@RequestBody Bikeshop bikeShop) {
        return bikeShopService.createShop(bikeShop);
    }
    
}
