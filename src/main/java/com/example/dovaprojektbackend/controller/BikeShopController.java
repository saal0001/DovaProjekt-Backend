package com.example.dovaprojektbackend.controller;


import com.example.dovaprojektbackend.dto.UpdateBikeshopRequest;
import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.security.CustomUserPrincipal;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final SupabaseAuthService supabaseAuthService;

    public BikeShopController(BikeShopService bikeShopService, @Autowired(required = false) SupabaseAuthService supabaseAuthService) {
        this.bikeShopService = bikeShopService;
        this.supabaseAuthService = supabaseAuthService;
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<Bikeshop> getBikeShopById(@PathVariable UUID shopId) {
        Bikeshop bikeshop = bikeShopService.getBikeShopById(shopId);
        return ResponseEntity.ok(bikeshop);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Bikeshop> updateMyProfile(@Valid @RequestBody UpdateBikeshopRequest request, @AuthenticationPrincipal CustomUserPrincipal principal){
        Bikeshop updateBikeShop = bikeShopService.updateBikeshop(request, principal.getUserId());
        return ResponseEntity.ok(updateBikeShop);
    }

    @DeleteMapping("/account")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal CustomUserPrincipal principal){
        bikeShopService.deleteBikeShop(principal.getUserId());
        supabaseAuthService.deleteUser(principal.getUserId());
        return ResponseEntity.ok().build();
    }

}
