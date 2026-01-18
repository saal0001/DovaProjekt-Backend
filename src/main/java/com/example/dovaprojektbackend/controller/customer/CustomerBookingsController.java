package com.example.dovaprojektbackend.controller.customer;

import com.example.dovaprojektbackend.dto.BookingStatusDto;
import com.example.dovaprojektbackend.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.example.dovaprojektbackend.dto.BookingDto;
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
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingDto request, @AuthenticationPrincipal CustomUserPrincipal principal) {
        Booking booking = bookingsService.createBooking(
                principal.getUserId(),
                request.getShopServiceId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID bookingId, @AuthenticationPrincipal CustomUserPrincipal principal) {
        bookingsService.cancelBooking(bookingId, principal.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Booking>> getMyBookings(@AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(bookingsService.getCustomerBookings(principal.getUserId()));
    }

    @GetMapping("/shop")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<List<Booking>> getShopBookings(@AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(bookingsService.getBookings(principal.getUserId()));
    }

    @PutMapping("/{bookingId}/status")
    @PreAuthorize("hasRole('SHOP')")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable UUID bookingId, @Valid @RequestBody BookingStatusDto request, @AuthenticationPrincipal CustomUserPrincipal principal) {
        Booking updatedBooking = bookingsService.updateBookingStatus(bookingId, request.getStatus(), principal.getUserId());
        return ResponseEntity.ok(updatedBooking);
    }
}