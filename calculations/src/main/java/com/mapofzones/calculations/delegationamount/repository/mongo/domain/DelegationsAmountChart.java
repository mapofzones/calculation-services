package com.mapofzones.calculations.delegationamount.repository.mongo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "delegationsAmountChart")
public class DelegationsAmountChart {

    private Data data;

    public DelegationsAmountChart(String zone) {
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
        public String zone;
        public List<Chart> chart;

        @Getter
        @Setter
        public static class Chart {
            public Long time;
            public BigDecimal value;
        }
    }

    public DelegationsAmountChart withPeriod(Long fromDate) {
        this.data.chart = this.data.chart.stream().filter(ch -> ch.getTime() >= fromDate).collect(Collectors.toList());
        return this;
    }


}
