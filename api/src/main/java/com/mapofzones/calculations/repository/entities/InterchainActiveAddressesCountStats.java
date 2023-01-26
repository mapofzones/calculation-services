package com.mapofzones.calculations.repository.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("interchainActiveAddressesCountStats")
public class InterchainActiveAddressesCountStats {

    private Data data;

    @Getter
    @Setter
    public static class Data {
        private Integer totalInterchainUniqueActiveAddressesDay;
        private Integer totalInterchainUniqueActiveAddressesWeek;
        private Integer totalInterchainUniqueActiveAddressesMonth;
    }
}
