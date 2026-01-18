package com.example.dovaprojektbackend.controller.customer;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.security.CustomUserPrincipal;
import com.example.dovaprojektbackend.service.BikeShopService;
import com.example.dovaprojektbackend.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerProfileController {

    private final CustomerService customerService;

    private final BikeShopService bikeShopService;

    public CustomerProfileController(CustomerService customerService, BikeShopService bikeShopService) {
        this.customerService = customerService;
        this.bikeShopService = bikeShopService;
    }

    // Hent alle shops for kunder (public endpoint - ingen login krævet)
    @GetMapping("/bikeshops")
    public ResponseEntity<List<Bikeshop>> findAllBikeshops(){
        return ResponseEntity.ok(bikeShopService.findAll());
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Customer> getMyProfile(@AuthenticationPrincipal CustomUserPrincipal principal) {
        Customer customer = customerService.getCustomerById(principal.getUserId());
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Customer> updateMyProfile(@Valid @RequestBody Customer customer, @AuthenticationPrincipal CustomUserPrincipal principal) {
        Customer customerUpdated = customerService.updateCustomer(principal.getUserId(), customer);
        return ResponseEntity.ok(customerUpdated);
    }


    @DeleteMapping("/account")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal CustomUserPrincipal principal) {
        customerService.deleteCustomer(principal.getUserId());
        return ResponseEntity.noContent().build();
    }
}