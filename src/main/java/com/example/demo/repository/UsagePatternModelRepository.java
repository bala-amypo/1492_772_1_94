package com.example.demo.repository;

import com.example.demo.entity.Bin;
import com.example.demo.entity.UsagePatternModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsagePatternModelRepository extends JpaRepository<UsagePatternModel, Long> {

    Optional<UsagePatternModel> findTopByBinOrderByLastUpdatedDesc(Bin bin);
}
