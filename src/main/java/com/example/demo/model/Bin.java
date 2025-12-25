package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "bins",
       uniqueConstraints = @UniqueConstraint(columnNames = "identifier"))
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String identifier;

    private String locationDescription;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Positive
    private Double capacityLiters;

    private Boolean active = true;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    // ===================== CONSTRUCTORS =====================

    // No-arg constructor (JPA)
    public Bin() {
    }

    // Main constructor (Timestamp)
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

    // ✅ Overloaded constructor (LocalDateTime) – REQUIRED FOR TESTS
    public Bin(String identifier,
               String locationDescription,
               Double latitude,
               Double longitude,
               Zone zone,
               Double capacityLiters,
               Boolean active,
               LocalDateTime createdAt,
               LocalDateTime updatedAt) {

        this(
                identifier,
                locationDescription,
                latitude,
                longitude,
                zone,
                capacityLiters,
                active,
                Timestamp.valueOf(createdAt),
                Timestamp.valueOf(updatedAt)
        );
    }

    // ===================== GETTERS & SETTERS =====================

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Double getCapacityLiters() {
        return capacityLiters;
    }

    public void setCapacityLiters(Double capacityLiters) {
        this.capacityLiters = capacityLiters;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    // ✅ ADD BELOW existing setters
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = Timestamp.valueOf(createdAt);
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = Timestamp.valueOf(updatedAt);
    }

}
