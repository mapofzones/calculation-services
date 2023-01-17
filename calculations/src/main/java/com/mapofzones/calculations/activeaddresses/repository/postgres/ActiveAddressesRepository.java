package com.mapofzones.calculations.activeaddresses.repository.postgres;

import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActiveAddressesRepository extends JpaRepository<ActiveAddress, ActiveAddress.ActiveAddressId> {

    @Query(value = """
            FROM ActiveAddress aa \
            WHERE aa.activeAddressId.datetime > ?1 \
            ORDER BY aa.activeAddressId.datetime ASC
            """)
    List<ActiveAddress> findAllByZoneForLastPeriod(LocalDateTime timeUntilNow);

}
