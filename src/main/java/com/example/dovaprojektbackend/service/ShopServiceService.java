package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.repository.ShopServiceRepository;

@Service
public class ShopServiceService {

    @Autowired
    private ShopServiceRepository ydelserRepository;
    @Autowired
    BikeshopRepository bikeshopRepository;

    public ShopService createYdelser(ShopService ydelser) {
        if (ydelser.getShopId() != null) {
            Bikeshop bikeshop = bikeshopRepository.findById(ydelser.getShopId())
                    .orElseThrow(() -> new RuntimeException("Bikeshop not found with id: " + ydelser.getShopId()));
            ydelser.setBikeshop(bikeshop);
        }

        return ydelserRepository.save(ydelser);
    }

}
