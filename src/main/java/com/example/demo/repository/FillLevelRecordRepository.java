package com.example.demo.repository;

import com.example.demo.entity.Bin;
import com.example.demo.entity.FillLevelRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FillLevelRecordRepository extends JpaRepository<FillLevelRecord, Long> {

    List<FillLevelRecord> findByBinOrderByRecordedAtDesc(Bin bin);

    Optional<FillLevelRecord> findTopByBinOrderByRecordedAtDesc(Bin bin);

    List<FillLevelRecord> findByBinAndRecordedAtBetween(
            Bin bin,
            LocalDateTime start,
            LocalDateTime end
    );
}
