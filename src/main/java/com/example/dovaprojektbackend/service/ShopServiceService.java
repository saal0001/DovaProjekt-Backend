package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
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
                if (shopId.equals(service.getBikeshop().getShopId())){
                    shopServices.add(service);
                }
            }
        }
        return shopServices;
    }

    public void deleteService(UUID serviceId){
            if (ydelserRepository.existsById(serviceId)){
                ydelserRepository.deleteById(serviceId);
        }else {
                throw new RuntimeException("service not found with id:" + serviceId);
            }
    }

    public ShopService updateService(ShopService service){
        ShopService oldService = new ShopService();
        if (ydelserRepository.existsById(service.getShopServiceId())){
            oldService = ydelserRepository.findById(service.getShopServiceId()).get();
            oldService.setName(service.getName());
            oldService.setPrice(service.getPrice());
            oldService.setDuration(service.getDuration());
            oldService.setDescription(service.getDescription());
            ydelserRepository.save(oldService);
        } else {
            throw new RuntimeException("service existere ikke");
        }
        return oldService;
    }

}
