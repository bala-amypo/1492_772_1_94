package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Model
public class UsagePatternModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    @ManyToOne
    private Bin bin;

    private Double avgDailyIncreaseWeekday;
    private Double avgDailyIncreaseWeekend;
    private LocalDateTime lastUpdated;

    // getters and setters
}


