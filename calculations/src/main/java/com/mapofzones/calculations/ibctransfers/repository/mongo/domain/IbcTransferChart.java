package com.mapofzones.calculations.ibctransfers.repository.mongo.domain;

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
@Document(collection = "ibcTransfersChart")
public class IbcTransferChart {

    private Data data;

    public IbcTransferChart(String zone) {
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
        private BigInteger totalIbcTransfersCount;
        private Integer totalPending;
        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            private Long time;
            private BigInteger pending;
            private BigInteger ibcTransfersCount;
        }
    }

    public IbcTransferChart withPeriod(Long fromDate) {
        this.data.chart = this.data.chart.stream().filter(ch -> ch.getTime() >= fromDate).collect(Collectors.toList());
        return this;
    }
}
