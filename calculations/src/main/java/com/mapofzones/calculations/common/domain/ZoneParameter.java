package com.mapofzones.calculations.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ZONE_PARAMETERS")
public class ZoneParameter extends ParameterEntity {

    @Column(name = "DELEGATION_AMOUNT")
    private BigDecimal delegationAmount;

    @Column(name = "UNDELEGATION_AMOUNT")
    private BigDecimal undelegationAmount;

    @Column(name = "DELEGATORS_COUNT")
    private Integer delegatorsCount;

}