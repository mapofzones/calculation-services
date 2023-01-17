package com.mapofzones.calculations.activeaddresses.service;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesChart;
import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ActiveAddressesCalculation {

    private ActiveAddressesCalculation(){}

    public static List<ActiveAddressesChart> buildChart(List<ActiveAddress> activeAddresses) {
        Map<String, Zone> zonesMap = new TreeMap<>();
        for (ActiveAddress currentActiveAddress : activeAddresses) {
            if (zonesMap.containsKey(currentActiveAddress.getActiveAddressId().getZone())) {
                zonesMap.get(currentActiveAddress.getActiveAddressId().getZone()).addTime(currentActiveAddress);
            } else {
                Zone zone = new Zone(currentActiveAddress);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<ActiveAddressesChart> activeAddressChart = new ArrayList<>();

        for (Map.Entry<String, Zone> zoneEntry : zonesMap.entrySet()) {
            ActiveAddressesChart zoneChart = new ActiveAddressesChart(zoneEntry.getKey());
            for (Map.Entry<LocalDateTime, List<Zone.Time>> timeEntry : zoneEntry.getValue().getTimeMap().entrySet()) {
                Integer activeAddressesCount = 0;
                Integer activeIbcAddressesCount = 0;

                for (Zone.Time time : timeEntry.getValue()) {
                    if (time.isInternalTransfer() || time.isInternalTx())
                        activeAddressesCount++;
                    if (time.isInternalTransfer())
                        activeIbcAddressesCount++;

                }

                ActiveAddressesChart.Data.ChartItem chartItem = new ActiveAddressesChart.Data.ChartItem(timeEntry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setActiveAddressesCount(activeAddressesCount);
                chartItem.setActiveIbcAddressesCount(activeIbcAddressesCount);
                zoneChart.getData().getChart().add(chartItem);
            }
            activeAddressChart.add(zoneChart);
        }

        return activeAddressChart;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"zone"})
    private static class Zone {
        String zone;
        Map<LocalDateTime, List<Time>> timeMap;

        public Zone(ActiveAddress activeAddress) {
            this.zone = activeAddress.getActiveAddressId().getZone();
            this.timeMap = new TreeMap<>();
            addTime(activeAddress);
        }

        public void addTime(ActiveAddress activeAddress) {
            Time time = new Time(activeAddress);

            if (timeMap.containsKey(time.dateTime)) {
                timeMap.get(time.dateTime).add(time);
            } else {
                List<Time> timeList = new ArrayList<>();
                timeList.add(time);
                timeMap.put(time.dateTime, timeList);
            }
        }

        @Getter
        @Setter
        @EqualsAndHashCode(of = {"dateTime"})
        private static class Time {

            LocalDateTime dateTime;
            boolean isInternalTransfer;
            boolean isInternalTx;
            boolean isExternalTransfer;

            public Time(ActiveAddress activeAddress) {
                this.dateTime = activeAddress.getActiveAddressId().getDatetime();
                this.isInternalTransfer = activeAddress.getIsInternalTransfer();
                this.isInternalTx = activeAddress.getIsInternalTx();
                this.isExternalTransfer = activeAddress.getIsExternalTransfer();
            }
        }
    }
}
