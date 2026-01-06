package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.service.ShopServiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.dovaprojektbackend.model.ShopService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ydelser")
public class ShopServiceController {

    private final ShopServiceService shopServiceService;

    public ShopServiceController(ShopServiceService shopServiceService) {
        this.shopServiceService = shopServiceService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('SHOP')")
    public ShopService createYdelse(@RequestBody ShopService shopService) {
        return shopServiceService.createYdelser(shopService);
    }

    @GetMapping("/shopServices")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SHOP')")
    public List<ShopService> getShopServices(@RequestParam UUID shopId) {
        return shopServiceService.getShopsService(shopId);
    }

    @DeleteMapping("/delete")
    public void deleteService(@RequestParam UUID serviceId){
        shopServiceService.deleteService(serviceId);
    }

    @PutMapping("/update")
    public ShopService updateService(@RequestBody ShopService shopService){
        return shopServiceService.updateService(shopService);
    }
    

}
