package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.service.ShopServiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.dovaprojektbackend.model.ShopService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ydelser")
//@PreAuthorize("hasAuthority('SHOP')")
public class ShopServiceController {


    private final ShopServiceService  shopServiceService;

    public ShopServiceController(ShopServiceService  shopServiceService) {
        this.shopServiceService = shopServiceService;
    }

    @PostMapping("/create")
    public ShopService createYdelse(@RequestBody ShopService shopService) {
        return shopServiceService.createYdelser(shopService);
    }

    @GetMapping("/shopServices")
    public List<ShopService> getShopServices(@RequestParam UUID shopId){
        return shopServiceService.getShopsService(shopId);
    }
    

}
