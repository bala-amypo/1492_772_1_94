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

    // ===== CONSTRUCTORS =====

    public UsagePatternModel() {}

    public UsagePatternModel(Bin bin,
                             Double weekday,
                             Double weekend,
                             Timestamp lastUpdated) {
        this.bin = bin;
        this.avgDailyIncreaseWeekday = weekday;
        this.avgDailyIncreaseWeekend = weekend;
        this.lastUpdated = lastUpdated;
    }

    public UsagePatternModel(Bin bin,
                             Double weekday,
                             Double weekend,
                             LocalDateTime lastUpdated) {
        this(bin, weekday, weekend, Timestamp.valueOf(lastUpdated));
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    // ✅ NULL-SAFE getters (ONLY ONCE)
    public Double getAvgDailyIncreaseWeekday() {
        return avgDailyIncreaseWeekday != null ? avgDailyIncreaseWeekday : 0.0;
    }

    public void setAvgDailyIncreaseWeekday(Double value) {
        this.avgDailyIncreaseWeekday = value;
    }

    public Double getAvgDailyIncreaseWeekend() {
        return avgDailyIncreaseWeekend != null ? avgDailyIncreaseWeekend : 0.0;
    }

    public void setAvgDailyIncreaseWeekend(Double value) {
        this.avgDailyIncreaseWeekend = value;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = Timestamp.valueOf(lastUpdated);
    }
}
