package com.example.demo.repository;

import com.example.demo.entity.OverflowPrediction;
import com.example.demo.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OverflowPredictionRepository extends JpaRepository<OverflowPrediction, Long> {

    @Query("""
        SELECT p FROM OverflowPrediction p
        WHERE p.bin.zone = :zone
        ORDER BY p.generatedAt DESC
    """)
    List<OverflowPrediction> findLatestPredictionsForZone(Zone zone);
}
