package com.example.dovaprojektbackend.model;

import jakarta.persistence.*;

@Entity

public class Ydelser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // private int shopId;
    private String name;
    private double price;
    private String duration;
    private String description;
    

}
