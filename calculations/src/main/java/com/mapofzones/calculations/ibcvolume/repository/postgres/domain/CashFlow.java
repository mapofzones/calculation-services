package com.mapofzones.calculations.ibcvolume.repository.postgres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "IBC_TRANSFER_HOURLY_CASHFLOW")
@NamedNativeQuery(name = "getCashFlowToken", resultSetMapping = "CashFlowTokenMapping", query = """
                    SELECT cf.zone, cf.zone_src, cf.zone_dest, cf.hour, \
                    cf.amount, cf.derivative_denom, d.origin_zone, t.base_denom, \
                    t.symbol_point_exponent, tp.coingecko_symbol_price_in_usd, tp.osmosis_symbol_price_in_usd, tp.sifchain_symbol_price_in_usd \
                        FROM ibc_transfer_hourly_cashflow cf \
                        JOIN derivatives d ON cf.derivative_denom = d.full_denom and cf.zone = d.zone \
                        JOIN tokens t on d.base_denom = t.base_denom and d.origin_zone = t.zone \
                        JOIN token_prices tp on t.base_denom = tp.base_denom and t.zone = tp.zone and cf.hour = tp.datetime \
                        WHERE cf.hour >= ?1 ORDER BY cf.hour
                    """)
@SqlResultSetMapping(name = "CashFlowTokenMapping",
    classes = {
        @ConstructorResult(columns = {
                @ColumnResult(name = "zone", type = String.class),
                @ColumnResult(name = "zone_src", type = String.class),
                @ColumnResult(name = "zone_dest", type = String.class),
                @ColumnResult(name = "hour", type = LocalDateTime.class),
                @ColumnResult(name = "amount", type = BigDecimal.class),
                @ColumnResult(name = "derivative_denom", type = String.class),
                @ColumnResult(name = "origin_zone", type = String.class),
                @ColumnResult(name = "base_denom", type = String.class),
                @ColumnResult(name = "symbol_point_exponent", type = Integer.class),
                @ColumnResult(name = "coingecko_symbol_price_in_usd", type = BigDecimal.class),
                @ColumnResult(name = "osmosis_symbol_price_in_usd", type = BigDecimal.class),
                @ColumnResult(name = "sifchain_symbol_price_in_usd", type = BigDecimal.class)
        }, targetClass = CashFlowToken.class)
    })
public class CashFlow {

    @Data
    @Embeddable
    public static class CashFlowId implements Serializable {
        @Column(name = "ZONE")
        private String zone;

        @Column(name = "ZONE_SRC")
        private String zoneSource;

        @Column(name = "ZONE_DEST")
        private String zoneDestination;

        @Column(name = "HOUR")
        private LocalDateTime hour;

        @Column(name = "PERIOD")
        private Integer period;

        @Column(name = "IBC_CHANNEL")
        private String ibcChannel;

        @Column(name = "DENOM")
        private String denom;
    }

    @EmbeddedId
    private CashFlowId cashFlowId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "DERIVATIVE_DENOM")
    private String derivativeDenom;
}

