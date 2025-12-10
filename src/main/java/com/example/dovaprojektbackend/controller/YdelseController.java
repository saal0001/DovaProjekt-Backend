package com.example.dovaprojektbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dovaprojektbackend.model.ShopService;
import com.example.dovaprojektbackend.service.YdelseService;

@RestController
@RequestMapping("/ydelser")
public class YdelseController {

    @Autowired
    YdelseService ydelseService;

    @PostMapping("/create")
    public ShopService createYdelse(@RequestBody ShopService shopService) {
        return ydelseService.createYdelser(shopService);
    }
    

}
