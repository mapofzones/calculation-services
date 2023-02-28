package com.mapofzones.calculations.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.mapofzones.calculations.common.postgres.repository.Queries.GET_DELEGATORS_COUNT;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ZONE_PARAMETERS")
@NamedNativeQueries(
        @NamedNativeQuery(name = "getDelegatorsCount", resultSetMapping = "delegatorsCountMapping", query = GET_DELEGATORS_COUNT)
)
@SqlResultSetMapping(name = "delegatorsCountMapping",
    classes = {
        @ConstructorResult(columns = {
            @ColumnResult(name = "ZONE", type = String.class),
            @ColumnResult(name = "DATETIME", type = LocalDateTime.class),
            @ColumnResult(name = "DELEGATORS_COUNT", type = Integer.class)
        }, targetClass = ZoneParameter.DelegatorsCountMapping.class)
})
public class ZoneParameter extends ParameterEntity {

    @Column(name = "DELEGATION_AMOUNT")
    private BigDecimal delegationAmount;

    @Column(name = "UNDELEGATION_AMOUNT")
    private BigDecimal undelegationAmount;

    @Column(name = "DELEGATORS_COUNT")
    private Integer delegatorsCount;

    @Data
    @AllArgsConstructor
    public static class DelegatorsCountMapping {

        private String zone;
        private LocalDateTime datetime;
        private Integer delegatorsCount;
    }
}
