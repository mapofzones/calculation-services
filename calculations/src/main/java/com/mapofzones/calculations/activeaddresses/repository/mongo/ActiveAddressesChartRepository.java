package com.mapofzones.calculations.activeaddresses.repository.mongo;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesChart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActiveAddressesChartRepository extends MongoRepository<ActiveAddressesChart, String> {

}
