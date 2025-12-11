package com.example.dovaprojektbackend.controller.user;

import com.example.dovaprojektbackend.model.User;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import com.example.dovaprojektbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('customer')")
public class UserProfileController {

    private final UserService userService;
    private final SupabaseAuthService supabaseAuthService;

    public UserProfileController(UserService userService, SupabaseAuthService supabaseAuthService) {
        this.userService = userService;
        this.supabaseAuthService = supabaseAuthService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        try{
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        try{
            User userUpdated = userService.updateUser(userId, user);
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete-account/{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID userId) {
        try{
            // 1. Slet i Supabase Auth
            supabaseAuthService.deleteUser(userId);

            // 2. Slet brugerens data i din backend
            userService.deleteUser(userId);

            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}