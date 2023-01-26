package com.mapofzones.calculations.transactions.repository.postgres;

import com.mapofzones.calculations.transactions.repository.postgres.domain.Tx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface TxsRepository extends JpaRepository<Tx, Tx.TxsId> {

    @Query("FROM Tx txs WHERE txs.id.datetime >= ?1 ORDER BY txs.id.datetime")
    List<Tx> findAllForLastPeriod(LocalDateTime period);
}
