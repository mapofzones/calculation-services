package com.mapofzones.calculations.transactions.repository.mongo;

import com.mapofzones.calculations.transactions.repository.mongo.domain.TxsChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TxsChartRepository extends MongoRepository<TxsChart, String> {

    @Transactional
    @Query("{'data.zone': {$eq: ?0}}")
    TxsChart findByData_Zone(String zone);

}
