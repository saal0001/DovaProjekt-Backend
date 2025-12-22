package com.example.dovaprojektbackend.dto;

import java.util.UUID;

public class CreateBookingRequest {

    private UUID userId;
    private UUID shopServiceId;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(UUID userId, UUID shopServiceId) {
        this.userId = userId;
        this.shopServiceId = shopServiceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getShopServiceId() {
        return shopServiceId;
    }

    public void setShopServiceId(UUID shopServiceId) {
        this.shopServiceId = shopServiceId;
    }
}