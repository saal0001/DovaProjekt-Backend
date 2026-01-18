package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.dto.UpdateBikeshopRequest;
import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BikeShopService {


    private final BikeshopRepository bikeShopRepository;

   public BikeShopService(BikeshopRepository bikeShopRepository){
       this.bikeShopRepository = bikeShopRepository;
   }


   public Bikeshop getBikeShopById(UUID id){
       return bikeShopRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Bikeshop not found with id: " + id));
   }

   public List<Bikeshop> findAll(){
       return bikeShopRepository.findAll();
   }

   @Transactional
   public Bikeshop updateBikeshop(UpdateBikeshopRequest request, UUID shopId ){
       Bikeshop bikeshop = getBikeShopById(shopId);

           bikeshop.setShopName(request.getShopName());
           bikeshop.setImageUrl(request.getImageUrl());
           bikeshop.setPhoneNumber(request.getPhoneNumber());
           bikeshop.setOpeningHours(request.getOpeningHours());
           bikeshop.setCvrNumber(request.getCvrNumber());
           bikeshop.setAddress(request.getAddress());
           bikeshop.setCity(request.getCity());
           bikeshop.setEmail(request.getEmail());

       return bikeShopRepository.save(bikeshop);
   }


   @Transactional
   public void deleteBikeShop(UUID shopId) {
       Bikeshop bikeshop = getBikeShopById(shopId);
       bikeShopRepository.delete(bikeshop);
   }

}
