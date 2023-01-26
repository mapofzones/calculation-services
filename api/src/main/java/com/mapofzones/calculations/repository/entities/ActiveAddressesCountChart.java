package com.mapofzones.calculations.repository.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Document("activeAddressesCountChart")
public class ActiveAddressesCountChart {

    private Data data;

    public void setTotalActiveAddressesCountStats(ActiveAddressesCountStats stats) {
        this.data.totalActiveAddressesDay = stats.getData().getStats().getTotalActiveAddressesDay();
        this.data.totalActiveAddressesWeek = stats.getData().getStats().getTotalActiveAddressesWeek();
        this.data.totalActiveAddressesMonth = stats.getData().getStats().getTotalActiveAddressesMonth();
        this.data.totalIbcActiveAddressesDay = stats.getData().getStats().getTotalIbcActiveAddressesDay();
        this.data.totalIbcActiveAddressesWeek = stats.getData().getStats().getTotalIbcActiveAddressesWeek();
        this.data.totalIbcActiveAddressesMonth = stats.getData().getStats().getTotalIbcActiveAddressesMonth();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {
        private String zone;
        private Integer totalActiveAddressesDay;
        private Integer totalActiveAddressesWeek;
        private Integer totalActiveAddressesMonth;
        private Integer totalIbcActiveAddressesDay;
        private Integer totalIbcActiveAddressesWeek;
        private Integer totalIbcActiveAddressesMonth;
        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            private Long time;
            private Integer activeAddressesCount;
            private Integer ibcActiveAddressesCount;
        }
    }

    public ActiveAddressesCountChart withPeriod(Long fromDate) {
        this.data.chart = this.data.chart.stream().filter(ch -> ch.getTime() >= fromDate).collect(Collectors.toList());
        return this;
    }
}
