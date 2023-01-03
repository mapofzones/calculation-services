package com.mapofzones.calculations.ibctransfers.repository.mongo;

import com.mapofzones.calculations.ibctransfers.repository.mongo.domain.IbcTransferChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IbcTransferChartRepository extends MongoRepository<IbcTransferChart, String> {

    @Transactional
    @Query("{'data.zone': {$eq: ?0}}")
    IbcTransferChart findByData_Zone(String zone);

}
