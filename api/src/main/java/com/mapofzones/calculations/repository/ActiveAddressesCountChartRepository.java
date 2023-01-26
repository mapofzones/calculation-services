package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.ActiveAddressesCountChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ActiveAddressesCountChartRepository extends MongoRepository<ActiveAddressesCountChart, String> {

    @Transactional
    ActiveAddressesCountChart findByData_Zone(String zone);

}
