package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeShopService {


    private final BikeshopRepository bikeShopRepository;

   public BikeShopService(BikeshopRepository bikeShopRepository){
       this.bikeShopRepository = bikeShopRepository;
   }

}
