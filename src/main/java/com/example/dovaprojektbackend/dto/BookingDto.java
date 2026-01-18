package com.example.dovaprojektbackend.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class BookingDto {

    @NotNull(message = "Service ID er påkrævet")
    private UUID shopServiceId;

    public BookingDto() {
    }

    public BookingDto(UUID shopServiceId) {
        this.shopServiceId = shopServiceId;
    }


    public UUID getShopServiceId() {
        return shopServiceId;
    }

    public void setShopServiceId(UUID shopServiceId) {
        this.shopServiceId = shopServiceId;
    }
}