package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.sql.Timestamp;

@Entity
@Table(name = "usage_pattern_models")
public class UsagePatternModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id", nullable = false)
    @NotNull(message = "Bin is required")
    private Bin bin;

    @NotNull(message = "Weekday daily increase is required")
    @PositiveOrZero(message = "Weekday daily increase must be >= 0")
    private Double avgDailyIncreaseWeekday;

    @NotNull(message = "Weekend daily increase is required")
    @PositiveOrZero(message = "Weekend daily increase must be >= 0")
    private Double avgDailyIncreaseWeekend;

    @Column(nullable = false)
    private Timestamp lastUpdated;

    // No-arg constructor
    public UsagePatternModel() {}

    // Parameterized constructor
    public UsagePatternModel(Bin bin,
                             Double avgDailyIncreaseWeekday,
                             Double avgDailyIncreaseWeekend,
                             Timestamp lastUpdated) {
        this.bin = bin;
        this.avgDailyIncreaseWeekday = avgDailyIncreaseWeekday;
        this.avgDailyIncreaseWeekend = avgDailyIncreaseWeekend;
        thi
