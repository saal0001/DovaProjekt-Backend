package com.example.dovaprojektbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.repository.ShopServiceRepository;

@Service
public class ShopServiceService {

    @Autowired
    private ShopServiceRepository ydelserRepository;

    public ShopService createYdelser(ShopService ydelser) {
        return ydelserRepository.save(ydelser);
    }
    
}
