package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_pattern_models")
public class UsagePatternModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id", nullable = false)
    @NotNull
    private Bin bin;

    @PositiveOrZero
    private Double avgDailyIncreaseWeekday;

    @PositiveOrZero
    private Double avgDailyIncreaseWeekend;

    private Timestamp lastUpdated;

    // ===================== CONSTRUCTORS =====================

    // No-arg constructor (JPA)
    public UsagePatternModel() {
    }

    // Main constructor (Timestamp)
    public UsagePatternModel(Bin bin,
                             Double avgDailyIncreaseWeekday,
                             Double avgDailyIncreaseWeekend,
                             Timestamp lastUpdated) {
        this.bin = bin;
        this.avgDailyIncreaseWeekday = avgDailyIncreaseWeekday;
        this.avgDailyIncreaseWeekend = avgDailyIncreaseWeekend;
        this.lastUpdated = lastUpdated;
    }

    // ✅ Overloaded constructor (LocalDateTime) – REQUIRED FOR TESTS
    public UsagePatternModel(Bin bin,
                             Double avgDailyIncreaseWeekday,
                             Double avgDailyIncreaseWeekend,
                             LocalDateTime lastUpdated) {

        this(
                bin,
                avgDailyIncreaseWeekday,
                avgDailyIncreaseWeekend,
                Timestamp.valueOf(lastUpdated)
        );
    }

    // ===================== GETTERS & SETTERS =====================

    public Long getId() {
        return id;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public Double getAvgDailyIncreaseWeekday() {
        return avgDailyIncreaseWeekday;
    }

    public void setAvgDailyIncreaseWeekday(Double avgDailyIncreaseWeekday) {
        this.avgDailyIncreaseWeekday = avgDailyIncreaseWeekday;
    }

    public Double getAvgDailyIncreaseWeekend() {
        return avgDailyIncreaseWeekend;
    }

    public void setAvgDailyIncreaseWeekend(Double avgDailyIncreaseWeekend) {
        this.avgDailyIncreaseWeekend = avgDailyIncreaseWeekend;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    // ✅ ADD BELOW existing setters
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = Timestamp.valueOf(lastUpdated);
    }

}
