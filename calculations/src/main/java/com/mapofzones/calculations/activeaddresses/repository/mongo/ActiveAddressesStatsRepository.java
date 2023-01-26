package com.mapofzones.calculations.activeaddresses.repository.mongo;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActiveAddressesStatsRepository extends MongoRepository<ActiveAddressesStats, String> {
}
