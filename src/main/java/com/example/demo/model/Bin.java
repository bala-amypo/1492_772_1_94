package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;

@Entity
@Table(
    name = "bins",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "identifier")
    }
)
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Identifier is required")
    @Column(nullable = false, unique = true)
    private String identifier;

    private String locationDescription;

    private Double latitude;

    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    @NotNull(message = "Zone is required")
    private Zone zone;

    @NotNull(message = "Capacity is required")
    @Positive(message = "capacity must be greater than 0")
    private Double capacityLiters;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    private Timestamp updatedAt;

    // No-arg constructor
    public Bin() {}

    // Parameterized constructor
    public Bin(String identifier,
               String locationDescription,
               Double latitude,
               Double longitude,
               Zone zone,
               Double capacityLiters,
               Boolean active,
               Timestamp createdAt,
               Timestamp updatedAt) {
        this.identifier = identifier;
        this.locationDescription = locationDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zone = zone;
        this.capacityLiters = capacityLiters;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = this.createdAt;
        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters omitted for brevity
}
