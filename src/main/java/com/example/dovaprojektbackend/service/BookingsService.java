package com.example.dovaprojektbackend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.repository.CustomerRepository;
import com.example.dovaprojektbackend.model.Bikeshop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dovaprojektbackend.model.Booking;
import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.model.enums.BookingStatus;
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

    @Transactional
    public Booking createBooking(UUID customerId, UUID shopServiceId) {
        // Verify customer exists
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer ikke fundet"));

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

    @Transactional
    public Booking cancelBooking(UUID bookingId, UUID customerId) {
        // Verify booking exists
        Booking booking = bookingsRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking ikke fundet"));

        // Verify booking belongs to customer
        if (!booking.getCustomer().getCustomerId().equals(customerId)) {
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
        if (shopId == null) {
            return new ArrayList<>();
        }
        return bookingsRepository.findByBikeshop_ShopId(shopId);
    }

    public List<Booking> getCustomerBookings(UUID customerId) {
        if (customerId == null) {
            return new ArrayList<>();
        }
        return bookingsRepository.findByCustomer_CustomerId(customerId);
    }

    @Transactional
    public Booking updateBookingStatus(UUID bookingId, BookingStatus newStatus, UUID shopId) {
        // Verify booking exists
        Booking booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking ikke fundet"));

        // Verify booking belongs to shop
        if (!booking.getBikeshop().getShopId().equals(shopId)) {
            throw new RuntimeException("Du kan kun opdatere bookinger for din egen butik");
        }

        // Update status
        booking.setStatus(newStatus);

        return bookingsRepository.save(booking);
    }
}