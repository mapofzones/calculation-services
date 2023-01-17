package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.DelegatorsCountChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DelegatorsCountChartRepository extends MongoRepository<DelegatorsCountChart, String> {

    @Transactional
//    @Query("{'data.zone': {$eq: ?0}}")
    DelegatorsCountChart findByData_Zone(String zone);

}
