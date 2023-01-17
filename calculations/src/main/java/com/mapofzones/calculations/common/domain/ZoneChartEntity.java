package com.mapofzones.calculations.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ZoneChartEntity extends AbstractMongoEntity {

    @Getter
    @Setter
    public static class AbstractData {
        public AbstractData(String zone) {
            this.zone = zone;
        }
        private String zone;
        @CreationTimestamp
        private LocalDate updateTime;

        @Getter
        @Setter
        public static class AbstractChartItem {
            public AbstractChartItem(Long time) {
                this.time = time;
            }
            public Long time;
        }
    }
}
