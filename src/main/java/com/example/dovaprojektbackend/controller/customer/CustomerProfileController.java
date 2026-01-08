package com.example.dovaprojektbackend.controller.customer;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.service.BikeShopService;
import com.example.dovaprojektbackend.service.CustomerService;
import com.example.dovaprojektbackend.service.SupabaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerProfileController {

    private final CustomerService customerService;
    private final SupabaseAuthService supabaseAuthService;
    private final BikeShopService bikeShopService;

    public CustomerProfileController(CustomerService customerService, @Autowired(required = false) SupabaseAuthService supabaseAuthService, BikeShopService bikeShopServic) {
        this.customerService = customerService;
        this.supabaseAuthService = supabaseAuthService;
        this.bikeShopService = bikeShopServic;
    }

    // Hent alle shops for kunder
    @GetMapping("/bikeshops")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SHOP')")
    public ResponseEntity<List<Bikeshop>> findAllBikeshops(){
        return ResponseEntity.ok(bikeShopService.findAll());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID userId) {
        Customer customer = customerService.getCustomerById(userId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID userId, @RequestBody Customer customer) {
        Customer customerUpdated = customerService.updateCustomer(userId, customer);
        return ResponseEntity.ok(customerUpdated);
    }

    @DeleteMapping("/delete-account/{userId}")
    @PreAuthorize("#userId == authentication.principal.getUserId() and hasRole('CUSTOMER')")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID userId) {
        customerService.deleteCustomer(userId);
        supabaseAuthService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}