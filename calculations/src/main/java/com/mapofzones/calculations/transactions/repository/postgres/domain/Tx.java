package com.mapofzones.calculations.transactions.repository.postgres.domain;

import com.mapofzones.calculations.common.domain.PostgresEntity;
import com.mapofzones.calculations.transactions.repository.postgres.TransactionsQueries;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "TOTAL_TX_HOURLY_STATS")
@NamedNativeQuery(name = "getTxCount", resultSetMapping = "TxCountMapping", query = TransactionsQueries.GET_TRANSACTIONS_COUNT)
@SqlResultSetMapping(name = "TxCountMapping",
    classes = {
        @ConstructorResult(columns = {
            @ColumnResult(name = "ZONE", type = String.class),
            @ColumnResult(name = "HOUR", type = LocalDateTime.class),
            @ColumnResult(name = "TXS_CNT", type = Integer.class)
        }, targetClass = CustomTx.class)
    })
public class Tx extends PostgresEntity {

    @EmbeddedId
    private TxsId id;

    @Column(name = "TXS_CNT")
    private Integer txsCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class TxsId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "HOUR")
        private LocalDateTime datetime;

        @Column(name = "PERIOD")
        private Integer period;
    }
}
