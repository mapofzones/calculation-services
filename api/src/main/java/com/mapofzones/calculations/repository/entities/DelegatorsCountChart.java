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
@Document(collection = "delegatorsCountChart")
public class DelegatorsCountChart {

    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {
        public String zone;
        public List<Chart> chart;

        @Getter
        @Setter
        public static class Chart {
            public Long time;
            public Integer delegatorsCount;
        }
    }

    public DelegatorsCountChart withPeriod(Long fromDate) {
        this.data.chart = this.data.chart.stream().filter(ch -> ch.getTime() >= fromDate).collect(Collectors.toList());
        return this;
    }
}
