package com.mapofzones.calculations.common;

import com.mapofzones.calculations.common.domain.MongoEntity;
import com.mapofzones.calculations.common.domain.PostgresEntity;

public interface Calculation<M extends MongoEntity, P extends PostgresEntity> {

    M doCalculationByZone(P postgresEntity);

}
