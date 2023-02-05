package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.TxsChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TxsChartRepository extends MongoRepository<TxsChart, String> {

    @Transactional
    @Query(value = "{'data.zone': '?0'}", fields = "{'data.chart': {$slice: ?1}}")
    TxsChart findByData_Zone(String zone, Long period);

}
