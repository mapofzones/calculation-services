package com.mapofzones.calculations.common;

import com.mapofzones.calculations.common.domain.AbstractMongoEntity;
import com.mapofzones.calculations.common.domain.PostgresEntity;

public interface Calculation<M extends AbstractMongoEntity, P extends PostgresEntity> {

    M doCalculationByZone(P postgresEntity);

}
