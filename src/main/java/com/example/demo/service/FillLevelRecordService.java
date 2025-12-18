package com.example.demo.service;

import com.example.demo.entity.FillLevelRecord;

import java.util.List;

public interface FillLevelRecordService {

    FillLevelRecord createRecord(FillLevelRecord record);

    List<FillLevelRecord> getRecordsForBin(Long binId);

    FillLevelRecord getRecordById(Long id);

    List<FillLevelRecord> getRecentRecords(Long binId, int limit);
}
