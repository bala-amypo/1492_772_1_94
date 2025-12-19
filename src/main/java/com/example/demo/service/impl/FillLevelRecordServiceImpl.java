package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.FillLevelRecord;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.FillLevelRecordRepository;
import com.example.demo.service.FillLevelRecordService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class FillLevelRecordServiceImpl implements FillLevelRecordService {

    private final FillLevelRecordRepository recordRepository;
    private final BinRepository binRepository;

    // Constructor injection ONLY
    public FillLevelRecordServiceImpl(
            FillLevelRecordRepository recordRepository,
            BinRepository binRepository) {
        this.recordRepository = recordRepository;
        this.binRepository = binRepository;
    }

    @Override
    public FillLevelRecord createRecord(FillLevelRecord record) {

        // Validate fill percentage
        if (record.getFillPercentage() < 0 || record.getFillPercentage() > 100) {
            throw new BadRequestException(
                    "fillPercentage must be between 0 and 100");
        }

        // Validate recordedAt not in future
        if (record.getRecordedAt().after(Timestamp.from(Instant.now()))) {
            throw new BadRequestException(
                    "recordedAt must not be in the future");
        }

        // Load bin
        Long binId = record.getBin().getId();
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bin not found with id " + binId));

        // Check bin active
        if (!Boolean.TRUE.equals(bin.getActive())) {
            throw new BadRequestException("Bin is inactive");
        }

        record.setBin(bin);

        return recordRepository.save(record);
    }

    @Override
    public FillLevelRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fill level record not found with id " + id));
    }

    @Override
    public List<FillLevelRecord> getRecordsForBin(Long binId) {

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bin not found with id " + binId));

        return recordRepository.findByBinOrderByRecordedAtDesc(bin);
    }

    @Override
    public List<FillLevelRecord> getRecentRecords(Long binId, int limit) {

        if (limit <= 0) {
            throw new BadRequestException("limit must be greater than 0");
        }

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bin not found with id " + binId));

        List<FillLevelRecord> records =
                recordRepository.findByBinOrderByRecordedAtDesc(bin);

        return records.stream()
                .limit(limit)
                .toList();
    }
}
