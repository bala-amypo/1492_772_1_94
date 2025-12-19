package com.example.demo.repository;

import com.example.demo.model.Bin;
import com.example.demo.model.UsagePatternModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsagePatternModelRepository
        extends JpaRepository<UsagePatternModel, Long> {

    Optional<UsagePatternModel> findTop1ByBinOrderByLastUpdatedDesc(Bin bin);
}
