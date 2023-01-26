package com.mapofzones.calculations.activeaddresses.repository.mongo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document("interchainActiveAddressesCountChart")
public class InterchainActiveAddressesChart {

    private Data data;

    public InterchainActiveAddressesChart() {
        this.data = new InterchainActiveAddressesChart.Data();
        this.data.setChart(new ArrayList<>());
    }

    @Getter
    @Setter
    public static class Data {

        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            public ChartItem(Long time) {
                this.time = time;
            }

            public Long time;
            public Integer interchainActiveAddressesCount;
        }
    }
}
