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

    private String zone;
    private LocalDateTime datetime;
    private Integer activeAddressesCount;
    private Integer ibcActiveAddressesCount;

    public boolean completeResult(ActiveAddressesResultMapping ibcActiveAddressesResult) {
        if (this.datetime.isEqual(ibcActiveAddressesResult.datetime) && this.zone.equals(ibcActiveAddressesResult.zone)) {
            if (ibcActiveAddressesResult.getActiveAddressesCount() == null)
                this.ibcActiveAddressesCount = 0;
            else this.ibcActiveAddressesCount = ibcActiveAddressesResult.getActiveAddressesCount();
            return true;
        }
        return false;
    }

}
