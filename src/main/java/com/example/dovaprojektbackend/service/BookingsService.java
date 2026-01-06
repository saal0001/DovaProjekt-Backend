package com.example.dovaprojektbackend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.repository.CustomerRepository;
import com.example.dovaprojektbackend.model.Bikeshop;
import org.springframework.stereotype.Service;

import com.example.dovaprojektbackend.model.Booking;
import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.model.enums.BookingStatus;
import com.example.dovaprojektbackend.model.enums.Role;
import com.example.dovaprojektbackend.repository.BookingsRepository;
import com.example.dovaprojektbackend.repository.ShopServiceRepository;

@Service
public class BookingsService {

    private final BookingsRepository bookingsRepository;
    private final CustomerRepository customerRepository;
    private final ShopServiceRepository shopServiceRepository;

    public BookingsService(BookingsRepository bookingsRepository, CustomerRepository customerRepository, ShopServiceRepository shopServiceRepository) {
        this.bookingsRepository = bookingsRepository;
        this.customerRepository = customerRepository;
        this.shopServiceRepository = shopServiceRepository;
    }

    public Booking createBooking(UUID userId, UUID shopServiceId) {
        // Verify user exists and is a customer
        Customer customer = customerRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Bruger ikke fundet"));

        if (customer.getRole() != Role.customer) {
            throw new RuntimeException("Kun kunder kan oprette bookinger");
        }

        // Verify shop service exists
        ShopService shopService = shopServiceRepository.findById(shopServiceId)
            .orElseThrow(() -> new RuntimeException("Service ikke fundet"));

        Bikeshop bikeshop = shopService.getBikeshop();

                // Create new booking with status NY_BOOKING
        Booking booking = new Booking(
            customer,
            bikeshop,
            shopService,
            BookingStatus.NY_BOOKING,
            LocalDateTime.now()
        );

        return bookingsRepository.save(booking);
    }

    public Booking cancelBooking(UUID bookingId, UUID userId) {
        // Verify booking exists
        Booking booking = bookingsRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking ikke fundet"));

        // Verify booking belongs to user
        if (!booking.getUser().getCustomerId().equals(userId)) {
            throw new RuntimeException("Du kan kun annullere dine egne bookinger");
        }

        // Verify booking can be cancelled
        if (booking.getStatus() == BookingStatus.AFHENTET) {
            throw new RuntimeException("Kan ikke annullere en afhentet booking");
        }

        if (booking.getStatus() == BookingStatus.ANNULLERET) {
            throw new RuntimeException("Booking er allerede annulleret");
        }

        // Update status to ANNULLERET
        booking.setStatus(BookingStatus.ANNULLERET);

        return bookingsRepository.save(booking);
    }

    public List<Booking> getBookings(UUID shopId){
        List<Booking> bookings = new ArrayList<>();
        if (shopId != null){
            for (Booking booking: bookingsRepository.findAll()) {
                if (booking.getBikeshop().getShopId().equals(shopId)){
                    bookings.add(booking);
                }
            }
        }
      return bookings;

    }
}