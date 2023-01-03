package com.mapofzones.calculations.ibcvolume.repository.postgres;

import com.mapofzones.calculations.ibcvolume.repository.postgres.domain.CashFlow;
import com.mapofzones.calculations.ibcvolume.repository.postgres.domain.CashFlowToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface CashFlowRepository extends JpaRepository<CashFlow, CashFlow.CashFlowId> {

    @Query(name = "getCashFlowToken", nativeQuery = true)
    List<CashFlowToken> findAllForLastPeriod(LocalDateTime timeUntilNow);

}
