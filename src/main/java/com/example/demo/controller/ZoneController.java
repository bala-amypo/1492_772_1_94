package com.example.demo.controller;

import com.example.demo.entity.Zone;
import com.example.demo.service.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public Zone create(@RequestBody Zone zone) {
        return zoneService.createZone(zone);
    }

    @GetMapping
    public List<Zone> getAll() {
        return zoneService.getAllZones();
    }
}
