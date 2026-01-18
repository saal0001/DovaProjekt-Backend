package com.example.dovaprojektbackend.dto;

import com.example.dovaprojektbackend.model.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

public class BookingStatusDto {

    @NotNull(message = "Status er påkrævet")
    private BookingStatus status;

    public BookingStatusDto() {
    }

    public BookingStatusDto(BookingStatus status) {
        this.status = status;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
