package com.example.dovaprojektbackend.controller;


import com.example.dovaprojektbackend.dto.BikeshopDto;
import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.dovaprojektbackend.service.BikeShopService;

import java.util.UUID;

@RestController
@RequestMapping("/api/shops")
public class BikeShopController {


    private final BikeShopService bikeShopService;


    public BikeShopController(BikeShopService bikeShopService) {
        this.bikeShopService = bikeShopService;
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<Bikeshop> getBikeShopById(@PathVariable UUID shopId) {
        Bikeshop bikeshop = bikeShopService.getBikeShopById(shopId);
        return ResponseEntity.ok(bikeshop);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Bikeshop> updateMyProfile(@Valid @RequestBody BikeshopDto request, @AuthenticationPrincipal CustomUserPrincipal principal){
        Bikeshop updateBikeShop = bikeShopService.updateBikeshop(request, principal.getUserId());
        return ResponseEntity.ok(updateBikeShop);
    }

    @DeleteMapping("/account")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal CustomUserPrincipal principal){
        bikeShopService.deleteBikeShop(principal.getUserId());
        return ResponseEntity.ok().build();
    }

}
