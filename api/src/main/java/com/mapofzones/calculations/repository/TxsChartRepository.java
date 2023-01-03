package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.TxsChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TxsChartRepository extends MongoRepository<TxsChart, String> {

    @Transactional
    //@Query("{'data.zone': {$eq: ?0}}")
    TxsChart findByData_Zone(String zone);

}
