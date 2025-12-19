package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.Zone;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.BinService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinServiceImpl implements BinService {

    private final BinRepository binRepository;
    private final ZoneRepository zoneRepository;

    public BinServiceImpl(BinRepository binRepository,
                          ZoneRepository zoneRepository) {
        this.binRepository = binRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Bin createBin(Bin bin) {

        if (bin.getCapacityLiters() <= 0) {
            throw new BadRequestException("capacity must be greater than 0");
        }

        binRepository.findByIdentifier(bin.getIdentifier())
                .ifPresent(b -> {
                    throw new BadRequestException("Bin identifier already exists");
                });

        Long zoneId = bin.getZone().getId();
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found with id " + zoneId));

        bin.setZone(zone);
        bin.setActive(true);

        return binRepository.save(bin);
    }

    @Override
    public Bin updateBin(Long id, Bin bin) {

        Bin existing = getBinById(id);

        existing.setLocationDescription(bin.getLocationDescription());
        existing.setLatitude(bin.getLatitude());
        existing.setLongitude(bin.getLongitude());

        if (bin.getCapacityLiters() != null && bin.getCapacityLiters() <= 0) {
            throw new BadRequestException("capacity must be greater than 0");
        }

        if (bin.getCapacityLiters() != null) {
            existing.setCapacityLiters(bin.getCapacityLiters());
        }

        if (bin.getZone() != null) {
            Zone zone = zoneRepository.findById(bin.getZone().getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Zone not found"));
            existing.setZone(zone);
        }

        return binRepository.save(existing);
    }

    @Override
    public Bin getBinById(Long id) {
        return binRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bin not found with id " + id));
    }

    @Override
    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    @Override
    public void deactivateBin(Long id) {
        Bin bin = getBinById(id);
        bin.setActive(false);
        binRepository.save(bin);
    }
}
