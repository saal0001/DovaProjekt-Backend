package com.example.dovaprojektbackend.dto;

import com.example.dovaprojektbackend.model.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateBookingStatusRequest {

    @NotNull(message = "Status er påkrævet")
    private BookingStatus status;

    public UpdateBookingStatusRequest() {
    }

    public UpdateBookingStatusRequest(BookingStatus status) {
        this.status = status;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
