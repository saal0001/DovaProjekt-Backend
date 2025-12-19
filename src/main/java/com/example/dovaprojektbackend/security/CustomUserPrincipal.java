package com.example.dovaprojektbackend.security;

import com.example.dovaprojektbackend.model.enums.Role;
import java.util.UUID;

public class CustomUserPrincipal {
    private final UUID userId;
    private final String email;
    private final Role role;

    public CustomUserPrincipal(UUID userId, String email, Role role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID userId() {
        return userId;
    }


    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}