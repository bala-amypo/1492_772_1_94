package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UsagePatternModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Bin bin;

    private Double avgDailyIncreaseWeekday;
    private Double avgDailyIncreaseWeekend;
    private LocalDateTime lastUpdated;

    // getters and setters
}
