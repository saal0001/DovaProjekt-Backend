package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.security.CustomUserPrincipal;
import com.example.dovaprojektbackend.service.ShopServiceService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.dovaprojektbackend.model.ShopService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ShopServiceController {

    private final ShopServiceService shopServiceService;

    public ShopServiceController(ShopServiceService shopServiceService) {
        this.shopServiceService = shopServiceService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('SHOP')")
    public ShopService createShopService(@Valid @RequestBody ShopService shopService) {
        return shopServiceService.createShopService(shopService);
    }

    // Hent services for en shop (public endpoint - ingen login krævet)
    @GetMapping("/shopServices")
    public List<ShopService> getShopServices(@RequestParam UUID shopId) {
        return shopServiceService.getShopServices(shopId);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('SHOP')")
    public void deleteService(@RequestParam UUID serviceId, Authentication authentication){
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        UUID shopId = userPrincipal.getUserId();
        shopServiceService.deleteService(serviceId, shopId);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SHOP')")
    public ShopService updateService(@Valid @RequestBody ShopService shopService, Authentication authentication){
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        UUID shopId = userPrincipal.getUserId();
        return shopServiceService.updateService(shopService, shopId);
    }
    

}
