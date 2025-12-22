package com.example.dovaprojektbackend.controller.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.example.dovaprojektbackend.dto.CreateBookingRequest;
import com.example.dovaprojektbackend.model.Booking;
import com.example.dovaprojektbackend.service.BookingsService;

@RestController
@RequestMapping("/api/bookings")
public class UserBookingsController {

    private final BookingsService bookingsService;

    public UserBookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingRequest request) {
        Booking booking = bookingsService.createBooking(request.getUserId(), request.getShopServiceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @DeleteMapping("/{bookingId}/user/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID bookingId, @PathVariable UUID userId) {
        Booking booking = bookingsService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok(booking);
    }
}