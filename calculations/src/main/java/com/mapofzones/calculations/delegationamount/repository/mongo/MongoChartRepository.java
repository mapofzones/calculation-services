package com.mapofzones.calculations.delegationamount.repository.mongo;

import com.mapofzones.calculations.delegationamount.repository.mongo.domain.DelegationsChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MongoChartRepository extends MongoRepository<DelegationsChart, String> {

    @Transactional
    @Query("{'data.zone': {$eq: ?0}}")
    DelegationsChart findByData_Zone(String zone);


//    DelegationsAmountChart findByData(String zone);

}
