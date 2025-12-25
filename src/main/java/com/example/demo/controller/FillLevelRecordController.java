package com.example.demo.controller;

import com.example.demo.model.FillLevelRecord;
import com.example.demo.service.FillLevelRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fill-records")
public class FillLevelRecordController {

    private final FillLevelRecordService recordService;

    public FillLevelRecordController(FillLevelRecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<FillLevelRecord> createRecord(
            @Valid @RequestBody FillLevelRecord record) {
        return new ResponseEntity<>(
                recordService.createRecord(record),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FillLevelRecord> getRecordById(
            @PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecordById(id));
    }

    @GetMapping("/bin/{binId}")
    public ResponseEntity<List<FillLevelRecord>> getRecordsForBin(
            @PathVariable Long binId) {
        return ResponseEntity.ok(
                recordService.getRecordsForBin(binId));
    }

    @GetMapping("/bin/{binId}/recent")
    public ResponseEntity<List<FillLevelRecord>> getRecentRecords(
            @PathVariable Long binId,
            @RequestParam(defaultValue = "5") int limit) {

        return ResponseEntity.ok(
                recordService.getRecentRecords(binId, limit));
    }
}
