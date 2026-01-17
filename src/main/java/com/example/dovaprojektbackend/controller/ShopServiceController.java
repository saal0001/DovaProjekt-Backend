package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.security.CustomUserPrincipal;
import com.example.dovaprojektbackend.service.ShopServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ShopService> createShopService(@Valid @RequestBody ShopService shopService, @AuthenticationPrincipal CustomUserPrincipal principal) {

        // Sæt shopId fra autentificeret bruger
        shopService.setShopId(principal.getUserId());

        ShopService shopServiceCreated = shopServiceService.createShopService(shopService);
        return ResponseEntity.status(HttpStatus.CREATED).body(shopServiceCreated);
    }

    // Hent services for en shop (public endpoint - ingen login krævet)
    @GetMapping("/shopServices")
    public ResponseEntity<List<ShopService>> getShopServices(@RequestParam UUID shopId) {
        List<ShopService> shopServices = shopServiceService.getShopServices(shopId);
        return ResponseEntity.ok(shopServices);
    }

    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Void> deleteService(@PathVariable UUID serviceId, @AuthenticationPrincipal CustomUserPrincipal principal){

        shopServiceService.deleteService(serviceId, principal.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{serviceId}")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<ShopService> updateService(@Valid @RequestBody ShopService shopService,   @PathVariable UUID serviceId, @AuthenticationPrincipal CustomUserPrincipal principal){

        shopService.setShopServiceId(serviceId);
        shopService.setShopId(principal.getUserId());

        ShopService updated = shopServiceService.updateService(
                shopService,
                principal.getUserId()
        );
        return ResponseEntity.ok(updated);
    }
}
