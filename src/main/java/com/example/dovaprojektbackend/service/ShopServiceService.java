package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.repository.ShopServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShopServiceService {


    private final ShopServiceRepository ydelserRepository;

    private final BikeshopRepository bikeshopRepository;

    public ShopServiceService(ShopServiceRepository ydelserRepository,BikeshopRepository bikeshopRepository ){
        this.ydelserRepository = ydelserRepository;
        this.bikeshopRepository = bikeshopRepository;
    }



    public ShopService createYdelser(ShopService ydelser) {
        if (ydelser.getShopId() != null) {
            Bikeshop bikeshop = bikeshopRepository.findById(ydelser.getShopId())
                    .orElseThrow(() -> new RuntimeException("Bikeshop not found with id: " + ydelser.getShopId()));
            ydelser.setBikeshop(bikeshop);
        }

        return ydelserRepository.save(ydelser);
    }

    public List<ShopService> getShopsService(UUID shopId){
        List<ShopService> shopServices = new ArrayList<>();
        if (shopId != null){
            for (ShopService service:ydelserRepository.findAll()) {
                if (shopId.equals(service.getBikeshop().getId())){
                    shopServices.add(service);
                }
            }
        }
        return shopServices;
    }

}
