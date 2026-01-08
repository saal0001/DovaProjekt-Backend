package com.example.dovaprojektbackend.controller.customer;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.example.dovaprojektbackend.dto.CreateBookingRequest;
import com.example.dovaprojektbackend.model.Booking;
import com.example.dovaprojektbackend.service.BookingsService;

@RestController
@RequestMapping("/api/bookings")
public class CustomerBookingsController {

    private final BookingsService bookingsService;

    public CustomerBookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        Booking booking = bookingsService.createBooking(request.getUserId(), request.getShopServiceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @DeleteMapping("/{bookingId}/user/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID bookingId, @PathVariable UUID userId) {
        Booking booking = bookingsService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/bookings")
    @PreAuthorize("#shopId == authentication.principal.getUserId() and hasRole('SHOP')")
    public List<Booking> getShopServices(@RequestParam UUID shopId) {
        return bookingsService.getBookings(shopId);
    }
}