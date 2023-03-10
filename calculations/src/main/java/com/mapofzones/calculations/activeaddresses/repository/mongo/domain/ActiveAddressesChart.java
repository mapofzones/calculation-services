package com.mapofzones.calculations.activeaddresses.repository.mongo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("activeAddressesCountChart")
public class ActiveAddressesChart {

    private Data data;

    public ActiveAddressesChart(String zone) {
        this.data = new Data(zone);
        this.data.setChart(new ArrayList<>());
    }

    @Getter
    @Setter
    public static class Data {
        public Data(String zone) {
            this.zone = zone;
        }

        private String zone;
        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            public ChartItem(Long time) {
                this.time = time;
            }

            public Long time;
            public Integer activeAddressesCount;
            public Integer ibcActiveAddressesCount;
        }
    }
}
