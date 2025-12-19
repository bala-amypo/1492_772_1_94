package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Zone;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    // Constructor injection ONLY
    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone createZone(Zone zone) {

        // Unique zone name validation
        zoneRepository.findByZoneName(zone.getZoneName())
                .ifPresent(z -> {
                    throw new BadRequestException(
                            "Zone name already exists");
                });

        zone.setActive(true);

        return zoneRepository.save(zone);
    }

    @Override
    public Zone updateZone(Long id, Zone zone) {

        Zone existing = getZoneById(id);

        if (!existing.getZoneName().equals(zone.getZoneName())) {
            zoneRepository.findByZoneName(zone.getZoneName())
                    .ifPresent(z -> {
                        throw new BadRequestException(
                                "Zone name already exists");
                    });
            existing.setZoneName(zone.getZoneName());
        }

        existing.setDescription(zone.getDescription());

        return zoneRepository.save(existing);
    }

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Zone not found with id " + id));
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @Override
    public void deactivateZone(Long id) {

        Zone zone = getZoneById(id);
        zone.setActive(false);
        zoneRepository.save(zone);
    }
}
