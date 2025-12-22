package com.example.dovaprojektbackend.service;


import com.example.dovaprojektbackend.dto.CreateBikeshopRequest;
import com.example.dovaprojektbackend.dto.UpdateBikeshopRequest;
import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BikeShopService {


    private final BikeshopRepository bikeShopRepository;

   public BikeShopService(BikeshopRepository bikeShopRepository){
       this.bikeShopRepository = bikeShopRepository;
   }

   public Bikeshop createShop(CreateBikeshopRequest request){
       Bikeshop bikeshop = new Bikeshop();
       bikeshop.setShopName(request.getShopName());
       bikeshop.setImageUrl(request.getImageUrl());
       bikeshop.setPhoneNumber(request.getPhoneNumber());
       bikeshop.setOpeningHours(request.getOpeningHours());
       bikeshop.setCvrNumber(request.getCvrNumber());
       bikeshop.setAddress(request.getAddress());
       bikeshop.setEmail(request.getEmail());

       return bikeShopRepository.save(bikeshop);
   }

   public Bikeshop getBikeShopById(UUID id){
       return bikeShopRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Bikeshop not found with id: " + id));
   }

   public List<Bikeshop> findAll(){
       return bikeShopRepository.findAll();
   }

   public Bikeshop updateBikeshop(UpdateBikeshopRequest request, UUID shopId ){
       Bikeshop bikeshop = getBikeShopById(shopId);

       if (request.getShopName() != null){
           bikeshop.setShopName(request.getShopName());
       }
       if(request.getImageUrl() != null){
           bikeshop.setImageUrl(request.getImageUrl());
       }
       if (request.getPhoneNumber() != null){
           bikeshop.setPhoneNumber(request.getPhoneNumber());
       }
       if(request.getOpeningHours() != null){
           bikeshop.setOpeningHours(request.getOpeningHours());
       }
       if(request.getCvrNumber() != null){
           bikeshop.setCvrNumber(request.getCvrNumber());
       }
       if (request.getAddress() != null){
           bikeshop.setAddress(request.getAddress());
       }
       if (request.getEmail() != null){
           bikeshop.setEmail(request.getEmail());
       }

       return bikeShopRepository.save(bikeshop);
   }


   public void deleteBikeShop(UUID shopId){
       Bikeshop bikeshop = getBikeShopById(shopId);
       bikeShopRepository.deleteById(bikeshop.getShopId());
   }

}
