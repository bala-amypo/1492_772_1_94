package com.example.demo.controller;

import com.example.demo.model.OverflowPrediction;
import com.example.demo.service.OverflowPredictionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
public class OverflowPredictionController {

    private final OverflowPredictionService predictionService;

    public OverflowPredictionController(
            OverflowPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/generate/{binId}")
    public ResponseEntity<OverflowPrediction> generatePrediction(
            @PathVariable Long binId) {

        return new ResponseEntity<>(
                predictionService.generatePrediction(binId),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OverflowPrediction> getPredictionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                predictionService.getPredictionById(id));
    }

    @GetMapping("/bin/{binId}")
    public ResponseEntity<List<OverflowPrediction>> getPredictionsForBin(
            @PathVariable Long binId) {

        return ResponseEntity.ok(
                predictionService.getPredictionsForBin(binId));
    }

    @GetMapping("/zone/{zoneId}/latest")
    public ResponseEntity<List<OverflowPrediction>>
    getLatestPredictionsForZone(@PathVariable Long zoneId) {

        return ResponseEntity.ok(
                predictionService.getLatestPredictionsForZone(zoneId));
    }
}
