package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.UsagePatternModel;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.UsagePatternModelRepository;
import com.example.demo.service.UsagePatternModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsagePatternModelServiceImpl
        implements UsagePatternModelService {

    private final UsagePatternModelRepository modelRepository;
    private final BinRepository binRepository;

    // Constructor injection ONLY
    public UsagePatternModelServiceImpl(
            UsagePatternModelRepository modelRepository,
            BinRepository binRepository) {
        this.modelRepository = modelRepository;
        this.binRepository = binRepository;
    }

    @Override
    public UsagePatternModel createModel(UsagePatternModel model) {

        // Validate non-negative values
        if (model.getAvgDailyIncreaseWeekday() < 0 ||
            model.getAvgDailyIncreaseWeekend() < 0) {
            throw new BadRequestException(
                    "Average daily increases must be non-negative");
        }

        // Attach existing bin
        Long binId = model.getBin().getId();
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bin not found with id " + binId));

        model.setBin(bin);

        return modelRepository.save(model);
    }

    @Override
    public UsagePatternModel updateModel(Long id, UsagePatternModel model) {

        UsagePatternModel existing =
                modelRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "UsagePatternModel not found with id " + id));

        if (model.getAvgDailyIncreaseWeekday() < 0 ||
            model.getAvgDailyIncreaseWeekend() < 0) {
            throw new BadRequestException(
                    "Average daily increases must be non-negative");
        }

        existing.setAvgDailyIncreaseWeekday(
                model.getAvgDailyIncreaseWeekday());
        existing.setAvgDailyIncreaseWeekend(
                model.getAvgDailyIncreaseWeekend());

        return modelRepository.save(existing);
    }

    @Override
    public UsagePatternModel getModelForBin(Long binId) {

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bin not found with id " + binId));

        return modelRepository
                .findTop1ByBinOrderByLastUpdatedDesc(bin)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No usage pattern model found for bin " + binId));
    }

    @Override
    public List<UsagePatternModel> getAllModels() {
        return modelRepository.findAll();
    }
}
