package com.mapofzones.calculations.ibctransfers.repository.postgres;

import com.mapofzones.calculations.ibctransfers.repository.postgres.domain.IbcTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IbcTransferRepository extends JpaRepository<IbcTransfer, IbcTransfer.IbcTransferId> {

    @Query("FROM IbcTransfer tr WHERE tr.id.datetime > ?1 ORDER BY tr.id.datetime")
    List<IbcTransfer> findAllForLastPeriod(LocalDateTime period);
}
