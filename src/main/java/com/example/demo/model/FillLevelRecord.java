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
    @NotNull
    private Bin bin;

    @NotNull
    @Min(0)
    @Max(100)
    private Double fillPercentage;

    @NotNull
    private Timestamp recordedAt;

    private Boolean isWeekend;

    // ===================== CONSTRUCTORS =====================

    public FillLevelRecord() {}

    public FillLevelRecord(Bin bin,
                           Double fillPercentage,
                           Timestamp recordedAt,
                           Boolean isWeekend) {
        this.bin = bin;
        this.fillPercentage = fillPercentage;
        this.recordedAt = recordedAt;
        this.isWeekend = isWeekend;
    }

    // For LocalDateTime + isWeekend
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

    // For tests (LocalDateTime only)
    public FillLevelRecord(Bin bin,
                           Double fillPercentage,
                           LocalDateTime recordedAt) {
        this(
                bin,
                fillPercentage,
                Timestamp.valueOf(recordedAt),
                false
        );
    }

    @PrePersist
    public void calculateWeekend() {
        if (recordedAt != null) {
            DayOfWeek day =
                    recordedAt.toLocalDateTime().getDayOfWeek();
            this.isWeekend =
                    (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
        }
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
    // ✅ ADD BELOW existing setters
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = Timestamp.valueOf(recordedAt);
    }

}
