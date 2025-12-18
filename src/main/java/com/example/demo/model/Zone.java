package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String zoneName;

    private String description;
    private Boolean active = true;

    // getters and setters
}
