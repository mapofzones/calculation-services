package com.mapofzones.calculations.activeaddresses.repository.mongo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("activeAddressesCountStats")
public class ActiveAddressesStats {

    private Data data;

    public ActiveAddressesStats(String zone) {
        this.data = new Data(zone);
    }

    @Getter
    @Setter
    public static class Data {

        private String zone;
        private StatsItem stats;

        public Data(String zone) {
            this.zone = zone;
            this.stats = new StatsItem();
        }

        public static class StatsItem {
            Integer totalActiveAddressesDay;
            Integer totalActiveAddressesWeek;
            Integer totalActiveAddressesMonth;
            Integer totalIbcActiveAddressesDay;
            Integer totalIbcActiveAddressesWeek;
            Integer totalIbcActiveAddressesMonth;

            public Integer getTotalActiveAddressesDay() {
                return totalActiveAddressesDay;
            }

            public void setTotalActiveAddressesDay(Integer totalActiveAddressesDay) {
                this.totalActiveAddressesDay = totalActiveAddressesDay != null ? totalActiveAddressesDay : 0;
            }

            public Integer getTotalActiveAddressesWeek() {
                return totalActiveAddressesWeek;
            }

            public void setTotalActiveAddressesWeek(Integer totalActiveAddressesWeek) {
                this.totalActiveAddressesWeek = totalActiveAddressesWeek != null ? totalActiveAddressesWeek : 0;
            }

            public Integer getTotalActiveAddressesMonth() {
                return totalActiveAddressesMonth;
            }

            public void setTotalActiveAddressesMonth(Integer totalActiveAddressesMonth) {
                this.totalActiveAddressesMonth = totalActiveAddressesMonth != null ? totalActiveAddressesMonth : 0;
            }

            public Integer getTotalIbcActiveAddressesDay() {
                return totalIbcActiveAddressesDay;
            }

            public void setTotalIbcActiveAddressesDay(Integer totalIbcActiveAddressesDay) {
                this.totalIbcActiveAddressesDay = totalIbcActiveAddressesDay != null ? totalIbcActiveAddressesDay : 0;
            }

            public Integer getTotalIbcActiveAddressesWeek() {
                return totalIbcActiveAddressesWeek;
            }

            public void setTotalIbcActiveAddressesWeek(Integer totalIbcActiveAddressesWeek) {
                this.totalIbcActiveAddressesWeek = totalIbcActiveAddressesWeek != null ? totalIbcActiveAddressesWeek : 0;
            }

            public Integer getTotalIbcActiveAddressesMonth() {
                return totalIbcActiveAddressesMonth;
            }

            public void setTotalIbcActiveAddressesMonth(Integer totalIbcActiveAddressesMonth) {
                this.totalIbcActiveAddressesMonth = totalIbcActiveAddressesMonth != null ? totalActiveAddressesDay : 0;
            }
        }
    }
}
