package com.example.demo.controller;
import com.example.demo.dto.BinDTO;
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
    public ResponseEntity<BinDTO> createBin(@Valid @RequestBody BinDTO binDTO) {
        return new ResponseEntity<>(binService.createBin(binDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BinDTO> updateBin(@PathVariable Long id,
                                            @Valid @RequestBody BinDTO binDTO) {
        return ResponseEntity.ok(binService.updateBin(id, binDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BinDTO> getBin(@PathVariable Long id) {
        return ResponseEntity.ok(binService.getBinById(id));
    }

    @GetMapping
    public ResponseEntity<List<BinDTO>> getAllBins() {
        return ResponseEntity.ok(binService.getAllBins());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateBin(@PathVariable Long id) {
        binService.deactivateBin(id);
        return ResponseEntity.noContent().build();
    }
}
