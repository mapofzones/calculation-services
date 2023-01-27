package com.mapofzones.calculations.repository.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

import static com.mapofzones.calculations.common.CommonConst.HOURS_IN_DAY;
import static com.mapofzones.calculations.common.CommonConst.HOURS_IN_WEEK;

@Getter
@Setter
@NoArgsConstructor
@Document("activeAddressesCountChart")
public class ActiveAddressesCountChart {

    private Data data;

    public void setTotalActiveAddressesCountStats(ActiveAddressesCountStats stats, Long period) {

        if (period <= HOURS_IN_DAY) {
            this.data.totalActiveAddresses = stats.getData().getStats().getTotalActiveAddressesDay();
            this.data.totalIbcActiveAddresses = stats.getData().getStats().getTotalIbcActiveAddressesDay();
        } else if (period <= HOURS_IN_WEEK) {
            this.data.totalActiveAddresses = stats.getData().getStats().getTotalActiveAddressesWeek();
            this.data.totalIbcActiveAddresses = stats.getData().getStats().getTotalIbcActiveAddressesWeek();
        } else {
            this.data.totalActiveAddresses = stats.getData().getStats().getTotalActiveAddressesMonth();
            this.data.totalIbcActiveAddresses = stats.getData().getStats().getTotalIbcActiveAddressesMonth();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {
        private String zone;
        private Integer totalActiveAddresses;
        private Integer totalIbcActiveAddresses;
        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            private Long time;
            private Integer activeAddressesCount;
            private Integer ibcActiveAddressesCount;
        }
    }
}
