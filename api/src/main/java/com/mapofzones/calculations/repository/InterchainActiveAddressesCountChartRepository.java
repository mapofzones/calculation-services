package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.InterchainActiveAddressesCountChart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterchainActiveAddressesCountChartRepository extends MongoRepository<InterchainActiveAddressesCountChart, String> {



}
