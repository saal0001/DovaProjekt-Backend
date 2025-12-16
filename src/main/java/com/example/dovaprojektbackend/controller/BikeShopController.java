package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.dovaprojektbackend.service.BikeShopService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shops")
public class BikeShopController {


    private final BikeShopService bikeShopService;
    private final SupabaseAuthService supabaseAuthService;

    public BikeShopController(BikeShopService bikeShopService, SupabaseAuthService supabaseAuthService) {
        this.bikeShopService = bikeShopService;
        this.supabaseAuthService = supabaseAuthService;
    }


    @PutMapping("/{shopId}")
    @PreAuthorize("#shopId == authentication.principal.shopId and hasRole('shop')")
    public ResponseEntity<Bikeshop> update(@RequestBody Bikeshop bikeshop, @PathVariable UUID shopId){
        Bikeshop updateBikeShop = bikeShopService.updateBikeshop(bikeshop, shopId);
        return ResponseEntity.ok(updateBikeShop);
    }

    @DeleteMapping("/delete-account/{shopId}")
    @PreAuthorize("#shopId == authentication.principal.shopId and hasRole('shop')")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID shopId){
        supabaseAuthService.deleteUser(shopId);
        bikeShopService.deleteBikeShop(shopId);
        return ResponseEntity.ok().build();
    }
    
}
