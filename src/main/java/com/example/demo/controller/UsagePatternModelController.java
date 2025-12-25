package com.example.demo.controller;

import com.example.demo.model.UsagePatternModel;
import com.example.demo.service.UsagePatternModelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class UsagePatternModelController {

    private final UsagePatternModelService modelService;

    public UsagePatternModelController(
            UsagePatternModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<UsagePatternModel> createModel(
            @Valid @RequestBody UsagePatternModel model) {

        return new ResponseEntity<>(
                modelService.createModel(model),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsagePatternModel> updateModel(
            @PathVariable Long id,
            @Valid @RequestBody UsagePatternModel model) {

        return ResponseEntity.ok(
                modelService.updateModel(id, model));
    }

    @GetMapping("/bin/{binId}")
    public ResponseEntity<UsagePatternModel> getModelForBin(
            @PathVariable Long binId) {

        return ResponseEntity.ok(
                modelService.getModelForBin(binId));
    }

    @GetMapping
    public ResponseEntity<List<UsagePatternModel>> getAllModels() {

        return ResponseEntity.ok(
                modelService.getAllModels());
    }
}
