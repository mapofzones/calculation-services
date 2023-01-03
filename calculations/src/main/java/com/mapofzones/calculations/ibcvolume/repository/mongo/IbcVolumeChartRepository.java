package com.mapofzones.calculations.ibcvolume.repository.mongo;

import com.mapofzones.calculations.ibcvolume.repository.mongo.domain.IbcVolumeChart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IbcVolumeChartRepository extends MongoRepository<IbcVolumeChart, String> {

    @Transactional
    @Query("{'data.zone': {$eq: ?0}}")
//    @Query("{'data.zone': {$eq: ?0}}")
//    find({"data.zone": "cheqd-mainnet-1"}).sort({"data.zone.time": 1}
    IbcVolumeChart findByData_Zone(String zone);

}
