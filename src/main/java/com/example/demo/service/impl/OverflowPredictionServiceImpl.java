package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.OverflowPredictionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class OverflowPredictionServiceImpl
        implements OverflowPredictionService {

    private final BinRepository binRepository;
    private final FillLevelRecordRepository recordRepository;
    private final UsagePatternModelRepository modelRepository;
    private final OverflowPredictionRepository predictionRepository;
    private final ZoneRepository zoneRepository;

    // Constructor injection ONLY
    public OverflowPredictionServiceImpl(
            BinRepository binRepository,
            FillLevelRecordRepository recordRepository,
            UsagePatternModelRepository modelRepository,
            OverflowPredictionRepository predictionRepository,
            ZoneRepository zoneRepository) {

        this.binRepository = binRepository;
        this.recordRepository = recordRepository;
        this.modelRepository = modelRepository;
        this.predictionRepository = predictionRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public OverflowPrediction generatePrediction(Long binId) {

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bin not found with id " + binId));

        if (!Boolean.TRUE.equals(bin.getActive())) {
            throw new BadRequestException("Bin is inactive");
        }

        FillLevelRecord latestRecord =
                recordRepository
                        .findTop1ByBinOrderByRecordedAtDesc(bin)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No fill level record found for bin"));

        UsagePatternModel model =
                modelRepository
                        .findTop1ByBinOrderByLastUpdatedDesc(bin)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No usage pattern model found for bin"));

        double remainingCapacity =
                100.0 - latestRecord.getFillPercentage();

        double dailyIncrease =
                latestRecord.getIsWeekend()
                        ? model.getAvgDailyIncreaseWeekend()
                        : model.getAvgDailyIncreaseWeekday();

        if (dailyIncrease <= 0) {
            throw new BadRequestException(
                    "Daily increase must be greater than 0 to predict overflow");
        }

        int daysUntilFull =
                (int) Math.ceil(remainingCapacity / dailyIncrease);

        if (daysUntilFull < 0) {
            throw new BadRequestException("daysUntilFull must be >= 0");
        }

        LocalDate predictedDate =
                LocalDate.now().plusDays(daysUntilFull);

        Date predictedFullDate =
                Date.from(predictedDate
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant());

        OverflowPrediction prediction =
                new OverflowPrediction(
                        bin,
                        predictedFullDate,
                        daysUntilFull,
                        model,
                        new Timestamp(System.currentTimeMillis()));

        return predictionRepository.save(prediction);
    }

    @Override
    public OverflowPrediction getPredictionById(Long id) {
        return predictionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Overflow prediction not found with id " + id));
    }

    @Override
    public List<OverflowPrediction> getPredictionsForBin(Long binId) {

        if (!binRepository.existsById(binId)) {
            throw new ResourceNotFoundException(
                    "Bin not found with id " + binId);
        }

        return predictionRepository.findByBinId(binId);
    }

    @Override
    public List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId) {

        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Zone not found with id " + zoneId));

        return predictionRepository
                .findLatestPredictionsForZone(zone);
    }
}
