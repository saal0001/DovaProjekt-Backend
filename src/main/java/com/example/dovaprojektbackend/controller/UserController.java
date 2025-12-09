package com.example.dovaprojektbackend.controller;

import com.example.dovaprojektbackend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Endpoint tilgængelig for alle autentificerede brugere
     */
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        // Authentication principal indeholder User objektet fra JwtAuthenticationFilter
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Endpoint kun tilgængelig for ADMIN rolle
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Dette endpoint er kun tilgængeligt for administratorer");
    }

    /**
     * Endpoint kun tilgængelig for SHOP rolle
     */
    @GetMapping("/shop")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<String> shopOnly() {
        return ResponseEntity.ok("Dette endpoint er kun tilgængeligt for butikker");
    }

    /**
     * Endpoint tilgængelig for CUSTOMER rolle
     */
    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> customerOnly() {
        return ResponseEntity.ok("Dette endpoint er kun tilgængeligt for kunder");
    }

    /**
     * Endpoint tilgængelig for både ADMIN og SHOP roller
     */
    @GetMapping("/admin-or-shop")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHOP')")
    public ResponseEntity<String> adminOrShop() {
        return ResponseEntity.ok("Dette endpoint er tilgængeligt for administratorer og butikker");
    }
}