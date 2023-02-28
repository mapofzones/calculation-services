package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.DelegatorsCountChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DelegatorsCountChartRepository extends MongoRepository<DelegatorsCountChart, String> {

    @Transactional
    @Query(value = "{'data.zone': '?0'}", fields = "{'data.chart': {$slice: ?1}}")
    DelegatorsCountChart findByData_Zone(String zone, Long period);

}
