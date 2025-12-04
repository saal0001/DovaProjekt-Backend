package com.example.dovaprojektbackend.model;

import jakarta.persistence.*;
import com.example.dovaprojektbackend.enums.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String name;
    private String email;
    private String password;
    private int phoneNumber;
    private String address;
    //@EnumeratedValue(EnumType.STRING)
   // private Role Role;
}
