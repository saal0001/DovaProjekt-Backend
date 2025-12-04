package com.example.dovaprojektbackend.dto.response;

import com.example.dovaprojektbackend.model.enums.Role;

import java.util.UUID;

public class LoginResponse {
    private UUID userId;
    private String email;
    private String name;
    private Role role;
    private String token;  // Spring Boot JWT token
    private String message;

    public LoginResponse() {}

    public LoginResponse(UUID userId, String email, String name, Role role, String token, String message) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.token = token;
        this.message = message;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
