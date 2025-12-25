package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Entity
@Table(name = "fill_level_records")
public class FillLevelRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id", nullable = false)
    @NotNull(message = "Bin is required")
    private Bin bin;

    @NotNull(message = "Fill percentage is required")
    @Min(value = 0, message = "Fill percentage must be between 0 and 100")
    @Max(value = 100, message = "Fill percentage must be between 0 and 100")
    private Double fillPercentage;

    @NotNull(message = "Recorded time is required")
    private Timestamp recordedAt;

    private Boolean isWeekend;

    // ===================== CONSTRUCTORS =====================

    // 1️⃣ No-arg constructor (JPA requirement)
    public FillLevelRecord() {
    }

    // 2️⃣ Main constructor (used by production code)
    public FillLevelRecord(Bin bin,
                           Double fillPercentage,
                           Timestamp recordedAt,
                           Boolean isWeekend) {
        this.bin = bin;
        this.fillPercentage = fillPercentage;
        this.recordedAt = recordedAt;
        this.isWeekend = isWeekend;
    }

    // 3️⃣ OVERLOADED constructor (for TESTS using LocalDateTime)
    public FillLevelRecord(Bin bin,
                           Double fillPercentage,
                           LocalDateTime recordedAt,
                           Boolean isWeekend) {

        this(
                bin,
                fillPercentage,
                Timestamp.valueOf(recordedAt),
                isWeekend
        );
    }

    // ===================== LIFECYCLE CALLBACK =====================

    @PrePersist
    public void calculateWeekend() {
        if (this.recordedAt != null) {
            LocalDateTime ldt = this.recordedAt.toLocalDateTime();
            DayOfWeek day = ldt.getDayOfWeek();
            this.isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
        }
    }

    // ===================== GETTERS & SETTERS =====================

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

    public Double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(Double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }
}
