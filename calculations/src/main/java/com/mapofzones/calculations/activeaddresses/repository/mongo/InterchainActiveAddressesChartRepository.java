package com.mapofzones.calculations.activeaddresses.repository.mongo;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.InterchainActiveAddressesChart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterchainActiveAddressesChartRepository extends MongoRepository<InterchainActiveAddressesChart, String> {
}
