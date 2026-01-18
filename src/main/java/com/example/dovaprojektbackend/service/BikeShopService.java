package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.dto.BikeshopDto;
import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BikeShopService {


    private final BikeshopRepository bikeShopRepository;
    private final SupabaseAuthService supabaseAuthService;

   public BikeShopService(BikeshopRepository bikeShopRepository, @Autowired(required = false) SupabaseAuthService supabaseAuthService){
       this.bikeShopRepository = bikeShopRepository;
       this.supabaseAuthService = supabaseAuthService;
   }


   public Bikeshop getBikeShopById(UUID id){
       return bikeShopRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Bikeshop not found with id: " + id));
   }

   public List<Bikeshop> findAll(){
       return bikeShopRepository.findAll();
   }


   public Bikeshop updateBikeshop(BikeshopDto request, UUID shopId ){
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



   public void deleteBikeShop(UUID shopId) {
       Bikeshop bikeshop = getBikeShopById(shopId);
       bikeShopRepository.delete(bikeshop);
       supabaseAuthService.deleteUser(shopId);
   }

}
