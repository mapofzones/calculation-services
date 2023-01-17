package com.mapofzones.calculations.common;

import com.mapofzones.calculations.common.domain.AbstractMongoEntity;
import com.mapofzones.calculations.common.domain.PostgresEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractCalculation implements Calculation<AbstractMongoEntity, PostgresEntity> {

    @Override
    public AbstractMongoEntity doCalculationByZone(PostgresEntity postgresEntity) {
        log.warn("Method doesn't support");
        return new AbstractMongoEntity();
    }
}
