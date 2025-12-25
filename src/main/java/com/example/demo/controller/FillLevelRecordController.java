package com.example.demo.controller;

import com.example.demo.dto.FillLevelRecordDTO;
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
    public ResponseEntity<FillLevelRecordDTO> createRecord(@Valid @RequestBody FillLevelRecordDTO dto) {
        return new ResponseEntity<>(recordService.createRecord(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FillLevelRecordDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecordById(id));
    }

    @GetMapping("/bin/{binId}")
    public ResponseEntity<List<FillLevelRecordDTO>> getRecordsForBin(@PathVariable Long binId) {
        return ResponseEntity.ok(recordService.getRecordsForBin(binId));
    }

    @GetMapping("/bin/{binId}/recent")
    public ResponseEntity<List<FillLevelRecordDTO>> getRecentRecords(
            @PathVariable Long binId,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(recordService.getRecentRecords(binId, limit));
    }
}
