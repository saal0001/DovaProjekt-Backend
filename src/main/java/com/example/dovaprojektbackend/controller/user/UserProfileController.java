package com.example.dovaprojektbackend.controller.user;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.model.User;
import com.example.dovaprojektbackend.service.BikeShopService;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import com.example.dovaprojektbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserService userService;
    private final SupabaseAuthService supabaseAuthService;
    private final BikeShopService bikeShopService;

    public UserProfileController(UserService userService, SupabaseAuthService supabaseAuthService, BikeShopService bikeShopServic) {
        this.userService = userService;
        this.supabaseAuthService = supabaseAuthService;
        this.bikeShopService = bikeShopServic;
    }

    // Hent alle shops for kunder
    @GetMapping("/bikeshops")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Bikeshop>> findAllBikeshops(){
        return ResponseEntity.ok(bikeShopService.findAll());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        User userUpdated = userService.updateUser(userId, user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/delete-account/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        supabaseAuthService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}