package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "zones")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String zoneName;

    private String description;

    private Boolean active = true;

    // ===================== CONSTRUCTORS =====================

    public Zone() {}

    public Zone(String zoneName,
                String description,
                Boolean active) {
        this.zoneName = zoneName;
        this.description = description;
        this.active = active;
    }

    // For tests (extra LocalDateTime param)
    public Zone(String zoneName,
                String description,
                Boolean active,
                java.time.LocalDateTime ignored) {
        this(zoneName, description, active);
    }

    // ===================== GETTERS & SETTERS =====================

    public Long getId() {
        return id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
