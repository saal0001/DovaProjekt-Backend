package com.example.dovaprojektbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.service.ServiceOfferingService;

@RestController
@RequestMapping("/ydelser")
@PreAuthorize("hasRole('SHOP')")
public class ShopServiceController {

    @Autowired
    ServiceOfferingService shopServiceService;

    @PostMapping("/create")
    public ShopService createYdelse(@RequestBody ShopService shopService) {
        return null;
    }
    

}
