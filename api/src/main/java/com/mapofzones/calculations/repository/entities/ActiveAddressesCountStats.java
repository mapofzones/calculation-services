package com.mapofzones.calculations.repository.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("activeAddressesCountStats")
public class ActiveAddressesCountStats {

    private Data data;

    @Getter
    @Setter
    public static class Data {

        private String zone;
        private StatsItem stats;

        @Getter
        @Setter
        public static class StatsItem {
            private Integer totalActiveAddressesDay;
            private Integer totalActiveAddressesWeek;
            private Integer totalActiveAddressesMonth;
            private Integer totalIbcActiveAddressesDay;
            private Integer totalIbcActiveAddressesWeek;
            private Integer totalIbcActiveAddressesMonth;
        }
    }
}
