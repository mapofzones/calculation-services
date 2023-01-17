package com.mapofzones.calculations.delegators.repository.mongo;

import com.mapofzones.calculations.delegators.repository.mongo.domain.DelegatorsCountChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DelegatorsCountChartRepository extends MongoRepository<DelegatorsCountChart, String> {

    @Transactional
    DelegatorsCountChart findByData_Zone(String zone);

}
