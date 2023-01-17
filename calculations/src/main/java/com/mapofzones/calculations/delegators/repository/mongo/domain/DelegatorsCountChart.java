package com.mapofzones.calculations.delegators.repository.mongo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "delegatorsCount")
public class DelegatorsCountChart {

    private Data data;

    public DelegatorsCountChart(String zone) {
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
            public Integer delegatorsCount;
        }
    }
}
