package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "overflow_predictions")
public class OverflowPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id", nullable = false)
    @NotNull(message = "Bin is required")
    private Bin bin;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date predictedFullDate;

    @NotNull
    @PositiveOrZero(message = "daysUntilFull must be >= 0")
    private Integer daysUntilFull;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    @NotNull(message = "UsagePatternModel is required")
    private UsagePatternModel modelUsed;

    @Column(nullable = false)
    private Timestamp generatedAt;

    // No-arg constructor
    public OverflowPrediction() {}

    // Parameterized constructor
    public OverflowPrediction(
            Bin bin,
            Date predictedFullDate,
            Integer daysUntilFull,
            UsagePatternModel modelUsed,
            Timestamp generatedAt) {
        this.bin = bin;
        this.predictedFullDate = predictedFullDate;
        this.daysUntilFull = daysUntilFull;
        this.modelUsed = modelUsed;
        this.generatedAt = generatedAt;
    }

    @PrePersist
    public void onCreate() {
        this.generatedAt = new Timestamp(System.currentTimeMillis());
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

    public Date getPredictedFullDate() {
        return predictedFullDate;
    }

    public void setPredictedFullDate(Date predictedFullDate) {
        this.predictedFullDate = predictedFullDate;
    }

    public Integer getDaysUntilFull() {
        return daysUntilFull;
    }

    public void setDaysUntilFull(Integer daysUntilFull) {
        this.daysUntilFull = daysUntilFull;
    }

    public UsagePatternModel getModelUsed() {
        return modelUsed;
    }

    public void setModelUsed(UsagePatternModel modelUsed) {
        this.modelUsed = modelUsed;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }
}
