package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.dto.CreateBikeshopRequest;
import com.example.dovaprojektbackend.dto.UpdateBikeshopRequest;
import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public BikeShopController(BikeShopService bikeShopService, @Autowired(required = false) SupabaseAuthService supabaseAuthService) {
        this.bikeShopService = bikeShopService;
        this.supabaseAuthService = supabaseAuthService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Bikeshop> createShop(@RequestBody CreateBikeshopRequest request){
        Bikeshop bikeshop = bikeShopService.createShop(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeshop);
    }

    @PutMapping("/{shopId}")
    @PreAuthorize("#shopId == authentication.principal.getUserId() and hasRole('SHOP')")
    public ResponseEntity<Bikeshop> update(@RequestBody UpdateBikeshopRequest request, @PathVariable UUID shopId){
        Bikeshop updateBikeShop = bikeShopService.updateBikeshop(request, shopId);
        return ResponseEntity.ok(updateBikeShop);
    }

    @DeleteMapping("/delete-account/{shopId}")
    @PreAuthorize("#shopId == authentication.principal.getUserId() and hasRole('SHOP')")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID shopId){
        bikeShopService.deleteBikeShop(shopId);
        supabaseAuthService.deleteUser(shopId);
        return ResponseEntity.ok().build();
    }

}
