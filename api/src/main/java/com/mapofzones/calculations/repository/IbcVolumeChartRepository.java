package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.IbcVolumeChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface IbcVolumeChartRepository extends MongoRepository<IbcVolumeChart, String> {

    @Transactional
    //@Query("{'data.zone': '?0'}")
    IbcVolumeChart findByData_Zone(String zone);

}
