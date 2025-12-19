package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "usage_pattern_models")
public class UsagePatternModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bin_id")
    private Bin bin;

    @NotNull
    private Double avgDailyIncreaseWeekday;

    @NotNull
    private Double avgDailyIncreaseWeekend;

    private Timestamp lastUpdated;

    // ✅ No-arg constructor
    public UsagePatternModel() {
    }

    // ✅ Parameterized constructor
    public UsagePatternModel(
            Bin bin,
            Double avgDailyIncreaseWeekday,
            Double avgDailyIncreaseWeekend,
            Timestamp lastUpdated) {

        this.bin = bin;
        this.avgDailyIncreaseWeekday = avgDailyIncreaseWeekday;
        this.avgDailyIncreaseWeekend = avgDailyIncreaseWeekend;
        this.lastUpdated = lastUpdated;
    }

    // 🔹 GETTERS & SETTERS 🔹

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
