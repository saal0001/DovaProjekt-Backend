package com.example.dovaprojektbackend.controller.user;

import com.example.dovaprojektbackend.model.User;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import com.example.dovaprojektbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserService userService;
    private final SupabaseAuthService supabaseAuthService;

    public UserProfileController(UserService userService, SupabaseAuthService supabaseAuthService) {
        this.userService = userService;
        this.supabaseAuthService = supabaseAuthService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId and hasRole('CUSTOMER')")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId and hasRole('CUSTOMER')")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        User userUpdated = userService.updateUser(userId, user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/delete-account/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId and hasRole('CUSTOMER')")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID userId) {
        supabaseAuthService.deleteUser(userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}