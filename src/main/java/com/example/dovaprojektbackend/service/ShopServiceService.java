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

    private final ShopServiceRepository shopServiceRepository;
    private final BikeshopRepository bikeshopRepository;

    public ShopServiceService(ShopServiceRepository shopServiceRepository, BikeshopRepository bikeshopRepository) {
        this.shopServiceRepository = shopServiceRepository;
        this.bikeshopRepository = bikeshopRepository;
    }



    public ShopService createShopService(ShopService shopService) {
        if (shopService.getShopId() != null) {
            Bikeshop bikeshop = bikeshopRepository.findById(shopService.getShopId())
                    .orElseThrow(() -> new RuntimeException("Bikeshop not found with id: " + shopService.getShopId()));
            shopService.setBikeshop(bikeshop);
        }

        return shopServiceRepository.save(shopService);
    }

    public List<ShopService> getShopServices(UUID shopId) {
        if (shopId == null) {
            return new ArrayList<>();
        }
        return shopServiceRepository.findByBikeshop_ShopId(shopId);
    }

    public void deleteService(UUID serviceId, UUID shopId) {
        ShopService service = shopServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));

        if (!service.getBikeshop().getShopId().equals(shopId)) {
            throw new RuntimeException("You are not authorized to delete this service");
        }

        shopServiceRepository.delete(service);
    }

    public ShopService updateService(ShopService service, UUID shopId) {
        ShopService existingService = shopServiceRepository.findById(service.getShopServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + service.getShopServiceId()));

        if (!existingService.getBikeshop().getShopId().equals(shopId)) {
            throw new RuntimeException("You are not authorized to update this service");
        }

        existingService.setName(service.getName());
        existingService.setPrice(service.getPrice());
        existingService.setDuration(service.getDuration());
        existingService.setDescription(service.getDescription());

        return shopServiceRepository.save(existingService);
    }

}
