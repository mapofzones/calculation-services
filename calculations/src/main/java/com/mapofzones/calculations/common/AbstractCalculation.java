package com.mapofzones.calculations.common;

import com.mapofzones.calculations.common.domain.MongoEntity;
import com.mapofzones.calculations.common.domain.PostgresEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractCalculation implements Calculation<MongoEntity, PostgresEntity> {

    @Override
    public MongoEntity doCalculationByZone(PostgresEntity postgresEntity) {
        log.warn("Method doesn't support");
        return new MongoEntity();
    }
}
