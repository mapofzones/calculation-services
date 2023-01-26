package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.ActiveAddressesCountStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ActiveAddressesCountStatsRepository extends MongoRepository<ActiveAddressesCountStats, String> {

    @Transactional
    ActiveAddressesCountStats findByData_Zone(String zone);

}
