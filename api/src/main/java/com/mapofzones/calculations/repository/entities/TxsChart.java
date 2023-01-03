package com.mapofzones.calculations.repository.entities;

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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {

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
