package com.mapofzones.calculations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {
        "com.mapofzones.calculations.delegations.repository.mongo",
        "com.mapofzones.calculations.delegators.repository.mongo",
        "com.mapofzones.calculations.ibcvolume.repository.mongo",
        "com.mapofzones.calculations.transactions.repository.mongo",
        "com.mapofzones.calculations.ibctransfers.repository.mongo",
        "com.mapofzones.calculations.activeaddresses.repository.mongo",
})
public class MongoConfig  {

}