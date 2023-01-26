package com.mapofzones.calculations.activeaddresses.repository.postgres;

import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddress;
import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddressesResultMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActiveAddressesRepository extends JpaRepository<ActiveAddress, ActiveAddress.ActiveAddressId> {

    @Query(name = "findActiveAddressesByPeriod", nativeQuery = true)
    List<ActiveAddressesResultMapping> findActiveAddresses(LocalDateTime time);

    @Query(name = "findIbcActiveAddressesByPeriod", nativeQuery = true)
    List<ActiveAddressesResultMapping> findIbcActiveAddresses(LocalDateTime time);

    @Query(name = "findUniqueActiveAddressesByPeriod", nativeQuery = true)
    List<ActiveAddressesResultMapping> findUniqueActiveAddresses(LocalDateTime time);

    @Query(name = "findTotalActiveAddressesByPeriod", nativeQuery = true)
    List<ActiveAddressesResultMapping> findTotalActiveAddressesCountByPeriod(LocalDateTime time);

    @Query(name = "findTotalIbcActiveAddressesByPeriod", nativeQuery = true)
    List<ActiveAddressesResultMapping> findTotalIbcActiveAddressesCountByPeriod(LocalDateTime time);

    @Query(value = """
            SELECT count(DISTINCT substr(aa.address, position('1' IN aa.address), length(aa.address) - (6 + position('1' IN aa.address) - 1))) AS "cutted_address" \
                FROM active_addresses aa \
                    WHERE aa.address != '' AND aa.hour >= ?1 \
                        AND (aa.is_internal_tx = TRUE OR aa.is_internal_transfer = TRUE)
            """, nativeQuery = true)
    Integer findTotalUniqueActiveAddressesCountByPeriod(LocalDateTime time);

}
