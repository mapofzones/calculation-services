package com.mapofzones.calculations.repository.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("interchainActiveAddressesCountChart")
public class InterchainActiveAddressesCountChart {

    @Getter
    @Setter
    public static class Data {

        private List<ChartItem> chart;

        @Getter
        @Setter
        public static class ChartItem {
            private Long time;
            private Integer interchainActiveAddressesCount;
        }
    }
}
