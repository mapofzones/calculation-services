package com.mapofzones.calculations.activeaddresses.service;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesChart;
import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesStats;
import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddressesResultMapping;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ActiveAddressesCalculation {

    private ActiveAddressesCalculation(){}

    public static List<ActiveAddressesChart> buildChart(List<ActiveAddressesResultMapping> activeAddressesResultMappingList) {
        Map<String, Zone> zonesMap = new TreeMap<>();
        for (ActiveAddressesResultMapping currentResultMapping : activeAddressesResultMappingList) {
            if (zonesMap.containsKey(currentResultMapping.getZone())) {
                zonesMap.get(currentResultMapping.getZone()).addTime(currentResultMapping);
            } else {
                Zone zone = new Zone(currentResultMapping);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<ActiveAddressesChart> activeAddressChart = new ArrayList<>();

        for (Map.Entry<String, Zone> zoneEntry : zonesMap.entrySet()) {
            ActiveAddressesChart zoneChart = new ActiveAddressesChart(zoneEntry.getKey());
            for (Map.Entry<LocalDateTime, Zone.Time> timeEntry : zoneEntry.getValue().getTimeMap().entrySet()) {
                ActiveAddressesChart.Data.ChartItem chartItem = new ActiveAddressesChart.Data.ChartItem(timeEntry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setActiveAddressesCount(timeEntry.getValue().getActiveAddressCount());
                chartItem.setIbcActiveAddressesCount(timeEntry.getValue().getIbcActiveAddressCount());
                zoneChart.getData().getChart().add(chartItem);
            }
            activeAddressChart.add(zoneChart);
        }
        return activeAddressChart;
    }

    public static List<ActiveAddressesStats> buildStats(List<ActiveAddressesResultMapping> activeAddressesResultMappingDay,
                                                        List<ActiveAddressesResultMapping> activeAddressesResultMappingWeek,
                                                        List<ActiveAddressesResultMapping> activeAddressesResultMappingMonth,
                                                        List<ActiveAddressesResultMapping> activeIbcAddressesResultMappingDay,
                                                        List<ActiveAddressesResultMapping> activeIbcAddressesResultMappingWeek,
                                                        List<ActiveAddressesResultMapping> activeIbcAddressesResultMappingMonth) {

        Map<String, ActiveAddressesStats> zoneActiveAddressesStats = activeAddressesResultMappingMonth.stream()
                .collect(Collectors.toMap(ActiveAddressesResultMapping::getZone, key -> {
                    ActiveAddressesStats stats = new ActiveAddressesStats(key.getZone());
                    stats.getData().getStats().setTotalActiveAddressesMonth(key.getActiveAddressesCount());
                    return stats;
                }));

        zoneActiveAddressesStats.forEach((key, value) ->
                value.getData().getStats().setTotalActiveAddressesDay(activeAddressesResultMappingDay.stream().filter(f ->
                        f.getZone().equals(key)).findFirst().orElse(new ActiveAddressesResultMapping(key, 0)).getActiveAddressesCount()));

        zoneActiveAddressesStats.forEach((key, value) ->
                value.getData().getStats().setTotalActiveAddressesWeek(activeAddressesResultMappingWeek.stream().filter(f ->
                        f.getZone().equals(key)).findFirst().orElse(new ActiveAddressesResultMapping(key, 0)).getActiveAddressesCount()));

        zoneActiveAddressesStats.forEach((key, value) ->
                value.getData().getStats().setTotalIbcActiveAddressesDay(activeIbcAddressesResultMappingDay.stream().filter(f ->
                        f.getZone().equals(key)).findFirst().orElse(new ActiveAddressesResultMapping(key, 0)).getActiveAddressesCount()));

        zoneActiveAddressesStats.forEach((key, value) ->
                value.getData().getStats().setTotalIbcActiveAddressesWeek(activeIbcAddressesResultMappingWeek.stream().filter(f ->
                        f.getZone().equals(key)).findFirst().orElse(new ActiveAddressesResultMapping(key, 0)).getActiveAddressesCount()));

        zoneActiveAddressesStats.forEach((key, value) ->
                value.getData().getStats().setTotalIbcActiveAddressesMonth(activeIbcAddressesResultMappingMonth.stream().filter(f ->
                        f.getZone().equals(key)).findFirst().orElse(new ActiveAddressesResultMapping(key, 0)).getActiveAddressesCount()));


        return zoneActiveAddressesStats.values().stream().toList();
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"zone"})
    private static class Zone {
        String zone;
        Map<LocalDateTime, Time> timeMap;

        public Zone(ActiveAddressesResultMapping activeAddressesResultMapping) {
            this.zone = activeAddressesResultMapping.getZone();
            this.timeMap = new TreeMap<>();
            addTime(activeAddressesResultMapping);
        }

        public void addTime(ActiveAddressesResultMapping activeAddressesResultMapping) {
            Time time = new Time(activeAddressesResultMapping);
            timeMap.put(time.dateTime, time);
        }

        @Getter
        @Setter
        @EqualsAndHashCode(of = {"dateTime"})
        private static class Time {

            LocalDateTime dateTime;
            Integer activeAddressCount;
            Integer ibcActiveAddressCount;

            public Time(ActiveAddressesResultMapping activeAddressesResultMapping) {
                this.dateTime = activeAddressesResultMapping.getDatetime();
                this.activeAddressCount = activeAddressesResultMapping.getActiveAddressesCount();
                this.ibcActiveAddressCount = activeAddressesResultMapping.getIbcActiveAddressesCount();
            }
        }
    }
}
