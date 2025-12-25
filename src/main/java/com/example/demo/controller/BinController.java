package com.example.demo.controller;

import com.example.demo.model.Bin;
import com.example.demo.service.BinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bins")
public class BinController {

    private final BinService binService;

    public BinController(BinService binService) {
        this.binService = binService;
    }

    @PostMapping
    public ResponseEntity<Bin> createBin(@Valid @RequestBody Bin bin) {
        return new ResponseEntity<>(binService.createBin(bin), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bin> updateBin(
            @PathVariable Long id,
            @Valid @RequestBody Bin bin) {
        return ResponseEntity.ok(binService.updateBin(id, bin));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bin> getBin(@PathVariable Long id) {
        return ResponseEntity.ok(binService.getBinById(id));
    }

    @GetMapping
    public ResponseEntity<List<Bin>> getAllBins() {
        return ResponseEntity.ok(binService.getAllBins());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateBin(@PathVariable Long id) {
        binService.deactivateBin(id);
        return ResponseEntity.noContent().build();
    }
}
