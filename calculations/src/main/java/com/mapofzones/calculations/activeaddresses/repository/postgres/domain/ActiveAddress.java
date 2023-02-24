package com.mapofzones.calculations.activeaddresses.repository.postgres.domain;

import com.mapofzones.calculations.activeaddresses.repository.postgres.ActiveAddressesQueries;
import com.mapofzones.calculations.common.domain.PostgresEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ACTIVE_ADDRESSES")
@NamedNativeQueries(value = {
    @NamedNativeQuery(name = "findActiveAddressesByPeriod", resultSetMapping = "activeAddressesMapping",
        query = ActiveAddressesQueries.GET_ACTIVE_ADDRESSES_BY_PERIOD),
    @NamedNativeQuery(name = "findUniqueActiveAddressesByPeriod", resultSetMapping = "uniqueActiveAddressesMapping",
        query = """
            SELECT unique_aa_count.hour, count(unique_aa_count.cutted_address) as "active_addresses_count" \
                FROM \
                    (SELECT DISTINCT aa.hour, substr(aa.address, position('1' IN aa.address), length(aa.address) - (6 + position('1' IN aa.address) - 1)) AS "cutted_address" \
                        FROM active_addresses aa \
                            WHERE address != '' \
                                AND aa.hour >= ?1 \
                                AND (aa.is_internal_tx = TRUE OR aa.is_internal_transfer = TRUE)) AS "unique_aa_count" \
                GROUP BY unique_aa_count.hour;
        """),
    @NamedNativeQuery(name = "findTotalActiveAddressesByPeriod", resultSetMapping = "totalActiveAddressesMapping",
        query = """
            SELECT aa.zone, count(DISTINCT aa.address) as "active_addresses_count" \
                FROM active_addresses aa \
                    WHERE aa.hour >= ?1 AND (aa.is_internal_tx = TRUE OR aa.is_internal_transfer = TRUE) \
                GROUP BY aa.zone;
    """),
    @NamedNativeQuery(name = "findTotalIbcActiveAddressesByPeriod", resultSetMapping = "totalActiveAddressesMapping",
        query = """
            SELECT aa.zone, count(DISTINCT aa.address) as "active_addresses_count" \
                FROM active_addresses aa \
                    WHERE aa.hour >= ?1 AND (aa.is_internal_transfer = TRUE) \
                GROUP BY aa.zone;
        """)
})
@SqlResultSetMappings(value = {
    @SqlResultSetMapping(name = "activeAddressesMapping", classes = {
        @ConstructorResult(columns = {
            @ColumnResult(name = "zone", type = String.class),
            @ColumnResult(name = "hour", type = LocalDateTime.class),
            @ColumnResult(name = "active_addresses", type = Integer.class),
            @ColumnResult(name = "ibc_active_addresses", type = Integer.class),
        }, targetClass = ActiveAddressesResultMapping.class)
    }),
    @SqlResultSetMapping(name = "uniqueActiveAddressesMapping", classes = {
        @ConstructorResult(columns = {
            @ColumnResult(name = "datetime", type = LocalDateTime.class),
            @ColumnResult(name = "active_addresses_count", type = Integer.class)
        }, targetClass = ActiveAddressesResultMapping.class)
    }),
    @SqlResultSetMapping(name = "totalActiveAddressesMapping", classes = {
        @ConstructorResult(columns = {
            @ColumnResult(name = "zone", type = String.class),
            @ColumnResult(name = "active_addresses_count", type = Integer.class)
        }, targetClass = ActiveAddressesResultMapping.class)
    })
})
public class ActiveAddress extends PostgresEntity {

    @EmbeddedId
    private ActiveAddressId activeAddressId;
    @Column(name = "is_internal_tx")
    private Boolean isInternalTx;
    @Column(name = "is_internal_transfer")
    private Boolean isInternalTransfer;
    @Column(name = "is_external_transfer")
    private Boolean isExternalTransfer;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class ActiveAddressId implements Serializable {

        @Column(name = "ADDRESS")
        private String address;
        @Column(name = "ZONE")
        private String zone;
        @Column(name = "HOUR")
        private LocalDateTime datetime;
        @Column(name = "PERIOD")
        private Integer period;

    }

}
