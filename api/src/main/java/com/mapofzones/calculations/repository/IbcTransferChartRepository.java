package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.IbcTransferChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IbcTransferChartRepository extends MongoRepository<IbcTransferChart, String> {

    @Transactional
    @Query(value = "{'data.zone': '?0'}", fields = "{'data.chart': {$slice: ?1}}")
    IbcTransferChart findByZoneAndPeriod(String zone, Long period);

}
