package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.ActiveAddressesCountChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ActiveAddressesCountChartRepository extends MongoRepository<ActiveAddressesCountChart, String> {

    @Transactional
    @Query(value = "{'data.zone': '?0'}", fields = "{'data.chart': {$slice: ?1}}")
    ActiveAddressesCountChart findByZoneAndPeriod(String zone, Long periodInDays);
}
