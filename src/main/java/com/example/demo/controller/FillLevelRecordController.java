package com.example.demo.controller;

import com.example.demo.entity.FillLevelRecord;
import com.example.demo.service.FillLevelRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fill-records")
public class FillLevelRecordController {

    private final FillLevelRecordService service;

    public FillLevelRecordController(FillLevelRecordService service) {
        this.service = service;
    }

    @PostMapping
    public FillLevelRecord create(@RequestBody FillLevelRecord record) {
        return service.createRecord(record);
    }

    @GetMapping("/bin/{binId}")
    public List<FillLevelRecord> getByBin(@PathVariable Long binId) {
        return service.getRecordsForBin(binId);
    }
}
