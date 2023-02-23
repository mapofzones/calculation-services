package com.mapofzones.calculations.transactions.repository.postgres;

import com.mapofzones.calculations.transactions.repository.postgres.domain.CustomTx;
import com.mapofzones.calculations.transactions.repository.postgres.domain.Tx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TxsRepository extends JpaRepository<Tx, Tx.TxsId> {

    @Query(name = "getTxCount", nativeQuery = true)
    List<CustomTx> getTxCount(LocalDateTime from, LocalDateTime to);
}
