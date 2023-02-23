package com.mapofzones.calculations.transactions.services;

import com.mapofzones.calculations.transactions.repository.mongo.domain.TxsChart;
import com.mapofzones.calculations.transactions.repository.postgres.domain.CustomTx;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TxsCalculation {

    private TxsCalculation() {}

    public static List<TxsChart> buildChart(List<CustomTx> txList) {
        Map<String, Zone> zonesMap = new TreeMap<>();
        for (CustomTx currentTx : txList) {
            if (zonesMap.containsKey(currentTx.getZone())) {
                zonesMap.get(currentTx.getZone()).addTime(currentTx);
            } else {
                Zone zone = new Zone(currentTx);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<TxsChart> txsCharts = new ArrayList<>();


        for (Map.Entry<String, Zone> zoneEntry : zonesMap.entrySet()) {
            TxsChart zoneChart = new TxsChart(zoneEntry.getKey());
            for (Map.Entry<LocalDateTime, List<Zone.Time>> timeEntry : zoneEntry.getValue().timeMap.entrySet()) {
                int txsCount = 0;
                for (Zone.Time time : timeEntry.getValue()) {
                    txsCount = txsCount + time.getTxCount();
                }
                TxsChart.Data.ChartItem chartItem = new TxsChart.Data.ChartItem();
                chartItem.setTime(timeEntry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setTxsCount(txsCount);
                zoneChart.getData().getChart().add(chartItem);
                zoneChart.getData().setTotalTxsCount(zoneChart.getData().getChart().stream().map(TxsChart.Data.ChartItem::getTxsCount).reduce(0, Integer::sum));
            }
            txsCharts.add(zoneChart);
        }
        return txsCharts;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"zone"})
    private static class Zone {
        String zone;
        Map<LocalDateTime, List<Time>> timeMap;

        public Zone(CustomTx tx) {
            this.zone = tx.getZone();
            this.timeMap = new TreeMap<>();
            addTime(tx);
        }

        public void addTime(CustomTx tx) {
            Time time = new Time(tx);

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
            Integer txCount;

            public Time(CustomTx tx) {
                this.dateTime = tx.getDatetime();
                this.txCount = tx.getTxsCount();
            }
        }
    }
}
