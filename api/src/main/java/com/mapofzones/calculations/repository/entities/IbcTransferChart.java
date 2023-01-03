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
@Document(collection = "ibcTransfersChart")
public class IbcTransferChart {

    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {

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
