package com.example.demo.repository;

import com.example.demo.model.Bin;
import com.example.demo.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BinRepository extends JpaRepository<Bin, Long> {

    Optional<Bin> findByIdentifier(String identifier);

    List<Bin> findByZoneAndActiveTrue(Zone zone);
}
