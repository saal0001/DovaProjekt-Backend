package com.example.dovaprojektbackend.service;


import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.stereotype.Service;

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

   public Bikeshop updateBikeshop(Bikeshop updateBikeShop, UUID shopId ){
       Bikeshop bikeshop = getBikeShopById(shopId);

       if (updateBikeShop.getShopName() != null){
           bikeshop.setShopName(updateBikeShop.getShopName());
       }
       if(updateBikeShop.getImageUrl() != null){
           bikeshop.setImageUrl(updateBikeShop.getImageUrl());
       }
       if (updateBikeShop.getPhoneNumber() != null){
           bikeshop.setPhoneNumber(updateBikeShop.getPhoneNumber());
       }
       if(updateBikeShop.getOpeningHours() != null){
           bikeshop.setOpeningHours(updateBikeShop.getOpeningHours());
       }
       if (updateBikeShop.getAddress() != null){
           bikeshop.setAddress(updateBikeShop.getAddress());
       }
       if (updateBikeShop.getEmail() != null){
           bikeshop.setEmail(updateBikeShop.getEmail());
       }

       return bikeShopRepository.save(bikeshop);
   }


   public void deleteBikeShop(UUID shopId){
       Bikeshop bikeshop = getBikeShopById(shopId);
       bikeShopRepository.deleteById(bikeshop.getShopId());
   }

}
