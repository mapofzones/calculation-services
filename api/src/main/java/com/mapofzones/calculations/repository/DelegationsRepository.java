package com.mapofzones.calculations.repository;

import com.mapofzones.calculations.repository.entities.DelegationsAmountChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DelegationsRepository extends MongoRepository<DelegationsAmountChart, String> {

    @Transactional
//    @Query("{'data.zone': {$eq: ?0}}")
    DelegationsAmountChart findByData_Zone(String zone);

}
