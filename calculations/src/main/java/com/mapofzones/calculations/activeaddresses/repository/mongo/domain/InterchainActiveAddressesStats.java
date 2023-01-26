package com.mapofzones.calculations.activeaddresses.repository.mongo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("interchainActiveAddressesCountStats")
public class InterchainActiveAddressesStats {

    private Data data;

    public InterchainActiveAddressesStats() {
        this.data = new Data();
    }

    @Getter
    @Setter
    public static class Data {
        private Integer totalInterchainUniqueActiveAddressesDay;
        private Integer totalInterchainUniqueActiveAddressesWeek;
        private Integer totalInterchainUniqueActiveAddressesMonth;
    }
}
