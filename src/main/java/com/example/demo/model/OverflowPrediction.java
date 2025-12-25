package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "overflow_predictions")
public class OverflowPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id")
    private Bin bin;

    private Date predictedFullDate;

    @PositiveOrZero
    private Integer daysUntilFull;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private UsagePatternModel modelUsed;

    private Timestamp generatedAt;

    // ===================== CONSTRUCTORS =====================

    public OverflowPrediction() {}

    public OverflowPrediction(Bin bin,
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

    // LocalDate + LocalDateTime
    public OverflowPrediction(Bin bin,
                              LocalDate predictedFullDate,
                              Integer daysUntilFull,
                              UsagePatternModel modelUsed,
                              LocalDateTime generatedAt) {
        this(
                bin,
                Date.valueOf(predictedFullDate),
                daysUntilFull,
                modelUsed,
                Timestamp.valueOf(generatedAt)
        );
    }

    // For tests (java.util.Date)
    public OverflowPrediction(Bin bin,
                              java.util.Date predictedFullDate,
                              Integer daysUntilFull,
                              UsagePatternModel modelUsed,
                              Timestamp generatedAt) {
        this(
                bin,
                new Date(predictedFullDate.getTime()),
                daysUntilFull,
                modelUsed,
                generatedAt
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
    // ✅ ADD BELOW existing setters
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = Timestamp.valueOf(generatedAt);
    }

    public void setPredictedFullDate(LocalDate predictedFullDate) {
        this.predictedFullDate = java.sql.Date.valueOf(predictedFullDate);
    }
    public void setGeneratedAt(java.time.LocalDateTime dt) {
        this.generatedAt = java.sql.Timestamp.valueOf(dt);
    }

    public void setPredictedFullDate(java.time.LocalDate date) {
        this.predictedFullDate = java.sql.Date.valueOf(date);
    }

}
