package com.example.demo.service;

import com.example.demo.model.Bin;

import java.util.List;

public interface BinService {

    Bin createBin(Bin bin);

    Bin updateBin(Long id, Bin bin);

    Bin getBinById(Long id);

    List<Bin> getAllBins();

    void deactivateBin(Long id);
}
