package com.example.demo.controller;

import com.example.demo.entity.OverflowPrediction;
import com.example.demo.service.OverflowPredictionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/predictions")
public class OverflowPredictionController {

    private final OverflowPredictionService service;

    public OverflowPredictionController(OverflowPredictionService service) {
        this.service = service;
    }

    @PostMapping("/bin/{binId}")
    public OverflowPrediction generate(@PathVariable Long binId) {
        return service.generatePrediction(binId);
    }

    @GetMapping("/zone/{zoneId}")
    public List<OverflowPrediction> getZonePredictions(@PathVariable Long zoneId) {
        return service.getLatestPredictionsForZone(zoneId);
    }
}
