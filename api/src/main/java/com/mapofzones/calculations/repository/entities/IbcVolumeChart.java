package com.mapofzones.calculations.repository.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "ibcVolumeChart")
public class IbcVolumeChart {

    private Data data;

    public IbcVolumeChart withPeriod(Long fromDate) {
        this.data.chart = this.data.chart.stream().filter(ch -> ch.getTime() >= fromDate)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Data.ChartItem::getTime))));
        return this;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {

        private String zone;
        private TreeSet<ChartItem> chart;

        @Getter
        @Setter
        @NoArgsConstructor
        @EqualsAndHashCode(of = {"time"})
        public static class ChartItem {

            private Long time;
            private LocalDateTime time1;
            private BigDecimal ibcIn;
            private BigDecimal ibcOut;
            private BigDecimal total;

        }
    }
}