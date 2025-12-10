package com.example.dovaprojektbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.repository.YdelseRepository;

@Service
public class YdelseService {

    @Autowired
    private YdelseRepository ydelserRepository;

    public ShopService createYdelser(ShopService ydelser) {
        return ydelserRepository.save(ydelser);
    }
    
}
