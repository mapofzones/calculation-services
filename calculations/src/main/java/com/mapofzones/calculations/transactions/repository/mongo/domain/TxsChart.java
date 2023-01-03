package com.mapofzones.calculations.transactions.repository.mongo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "txsChart")
public class TxsChart {

    private Data data;

    public TxsChart(String zone) {
        this.data = new Data(zone);
        this.data.setChart(new ArrayList<>());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {
        public Data(String zone) {
            this.zone = zone;
        }
        private String zone;
        private BigInteger totalTxsCount;
        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            private Long time;
            private BigInteger value;
        }
    }

    public TxsChart withPeriod(Long fromDate) {
        this.data.chart = this.data.chart.stream().filter(ch -> ch.getTime() >= fromDate).collect(Collectors.toList());
        return this;
    }
}
