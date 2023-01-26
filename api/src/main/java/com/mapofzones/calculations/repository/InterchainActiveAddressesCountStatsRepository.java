package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.InterchainActiveAddressesCountStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterchainActiveAddressesCountStatsRepository extends MongoRepository<InterchainActiveAddressesCountStats, String> {

}

