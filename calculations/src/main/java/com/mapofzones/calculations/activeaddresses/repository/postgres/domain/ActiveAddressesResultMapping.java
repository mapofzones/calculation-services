package com.mapofzones.calculations.activeaddresses.repository.postgres.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"zone", "datetime"})
public class ActiveAddressesResultMapping {

    public ActiveAddressesResultMapping(LocalDateTime datetime,
                                        Integer activeAddressesCount) {
        this.datetime = datetime;
        this.activeAddressesCount = activeAddressesCount;
    }

    public ActiveAddressesResultMapping(String zone,
                                        LocalDateTime datetime,
                                        Integer activeAddressesCount) {
        this.zone = zone;
        this.datetime = datetime;
        this.activeAddressesCount = activeAddressesCount;
    }

    public ActiveAddressesResultMapping(String zone,
                                        Integer activeAddressesCount) {
        this.zone = zone;
        this.activeAddressesCount = activeAddressesCount;
    }

    public ActiveAddressesResultMapping(String zone, LocalDateTime datetime, Integer activeAddressesCount, Integer ibcActiveAddressesCount) {
        this.zone = zone;
        this.datetime = datetime;
        this.activeAddressesCount = activeAddressesCount;
        this.ibcActiveAddressesCount = ibcActiveAddressesCount;
    }

    private String zone;
    private LocalDateTime datetime;
    private Integer activeAddressesCount;
    private Integer ibcActiveAddressesCount;

}
