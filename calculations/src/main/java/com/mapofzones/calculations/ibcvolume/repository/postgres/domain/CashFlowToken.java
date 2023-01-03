package com.mapofzones.calculations.ibcvolume.repository.postgres.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CashFlowToken {

    String zone;
    String zoneSource;
    String zoneDestination;
    LocalDateTime hour;
    BigDecimal amount;
    String derivativeDenom;
    String originZone;
    String baseDenom;
    Integer exponent;
    BigDecimal coingeckoTokenPrice;
    BigDecimal osmosisTokenPrice;
    BigDecimal sifchainTokenPrice;

}
