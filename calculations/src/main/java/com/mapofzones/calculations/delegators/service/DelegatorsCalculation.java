package com.mapofzones.calculations.delegators.service;

import com.mapofzones.calculations.common.domain.ZoneParameter;
import com.mapofzones.calculations.delegators.repository.mongo.domain.DelegatorsCountChart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DelegatorsCalculation {

    private DelegatorsCalculation() {}

    public static List<DelegatorsCountChart> buildChart(List<ZoneParameter.DelegatorsCountMapping> delegatorsCountList) {
        Map<String, DelegatorsCalculation.Zone> zonesMap = new TreeMap<>();
        for (ZoneParameter.DelegatorsCountMapping currentDelegatorsCount : delegatorsCountList) {
            if (zonesMap.containsKey(currentDelegatorsCount.getZone())) {
                zonesMap.get(currentDelegatorsCount.getZone()).addTime(currentDelegatorsCount);
            } else {
                DelegatorsCalculation.Zone zone = new DelegatorsCalculation.Zone(currentDelegatorsCount);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<DelegatorsCountChart> delegatorsCountCharts = new ArrayList<>();


        for (Map.Entry<String, DelegatorsCalculation.Zone> zoneEntry : zonesMap.entrySet()) {
            DelegatorsCountChart zoneChart = new DelegatorsCountChart(zoneEntry.getKey());
            for (Map.Entry<LocalDateTime, List<DelegatorsCalculation.Zone.Time>> timeEntry : zoneEntry.getValue().timeMap.entrySet()) {
                DelegatorsCountChart.Data.ChartItem chartItem = new DelegatorsCountChart.Data.ChartItem();
                chartItem.setTime(timeEntry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setDelegatorsCount(timeEntry.getValue().get(0).delegatorsCount);
                zoneChart.getData().getChart().add(chartItem);
            }
            delegatorsCountCharts.add(zoneChart);
        }
        return delegatorsCountCharts;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"zone"})
    private static class Zone {
        String zone;
        Map<LocalDateTime, List<DelegatorsCalculation.Zone.Time>> timeMap;

        public Zone(ZoneParameter.DelegatorsCountMapping delegatorsCountMapping) {
            this.zone = delegatorsCountMapping.getZone();
            this.timeMap = new TreeMap<>();
            addTime(delegatorsCountMapping);
        }

        public void addTime(ZoneParameter.DelegatorsCountMapping delegatorsCountMapping) {
            DelegatorsCalculation.Zone.Time time = new DelegatorsCalculation.Zone.Time(delegatorsCountMapping);

            if (timeMap.containsKey(time.dateTime)) {
                timeMap.get(time.dateTime).add(time);
            } else {
                List<DelegatorsCalculation.Zone.Time> timeList = new ArrayList<>();
                timeList.add(time);
                timeMap.put(time.dateTime, timeList);
            }
        }

        @Getter
        @Setter
        @EqualsAndHashCode(of = {"dateTime"})
        private static class Time {

            LocalDateTime dateTime;
            Integer delegatorsCount;

            public Time(ZoneParameter.DelegatorsCountMapping delegatorsCountMapping) {
                this.dateTime = delegatorsCountMapping.getDatetime();
                this.delegatorsCount = delegatorsCountMapping.getDelegatorsCount();
            }
        }
    }

}
