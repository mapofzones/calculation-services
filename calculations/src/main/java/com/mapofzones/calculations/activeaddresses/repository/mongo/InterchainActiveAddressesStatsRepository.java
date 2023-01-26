package com.mapofzones.calculations.activeaddresses.repository.mongo;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.InterchainActiveAddressesStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterchainActiveAddressesStatsRepository extends MongoRepository<InterchainActiveAddressesStats, String> {
}
