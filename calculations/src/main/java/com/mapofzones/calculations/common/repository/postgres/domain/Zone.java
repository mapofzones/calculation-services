package com.mapofzones.calculations.common.repository.postgres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ZONES")
public class Zone {

    @Id
    @Column(name = "CHAIN_ID")
    private String chainId;

    @Column(name = "BASE_TOKEN_DENOM")
    private String baseTokenDenom;

    @Column(name = "IS_MAINNET")
    private Boolean isMainnet;

}
