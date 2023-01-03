package com.mapofzones.calculations.delegationamount.repository.postgres;

import com.mapofzones.calculations.delegationamount.repository.postgres.domain.ZoneParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ZoneParametersRepository extends JpaRepository<ZoneParameter, ZoneParameter.ZoneParametersId> {

    @Query(value = """
            FROM ZoneParameter zp \
            WHERE zp.zoneParametersId.zone = ?1 AND zp.zoneParametersId.datetime > ?2 \
            ORDER BY zp.zoneParametersId.datetime ASC
            """)
    List<ZoneParameter> findAllByZoneForLastPeriod(String zone, LocalDateTime timeUntilNow);
}
