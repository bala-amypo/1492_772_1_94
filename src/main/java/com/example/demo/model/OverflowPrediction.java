package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class OverflowPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Bin bin;

    private LocalDate predictedFullDate;
    private Integer daysUntilFull;

    @ManyToOne
    private UsagePatternModel modelUsed;

    private LocalDateTime generatedAt;

    // getters and setters
}
