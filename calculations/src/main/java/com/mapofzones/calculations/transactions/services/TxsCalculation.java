package com.mapofzones.calculations.transactions.services;

import com.mapofzones.calculations.transactions.repository.mongo.domain.TxsChart;
import com.mapofzones.calculations.transactions.repository.postgres.domain.Tx;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TxsCalculation {

    private TxsCalculation() {}

    public static List<TxsChart> buildChart(List<Tx> txList) {
        Map<String, Zone> zonesMap = new TreeMap<>();
        for (Tx currentTx : txList) {
            if (zonesMap.containsKey(currentTx.getId().getZone())) {
                zonesMap.get(currentTx.getId().getZone()).addTime(currentTx);
            } else {
                Zone zone = new Zone(currentTx);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<TxsChart> txsCharts = new ArrayList<>();


        for (Map.Entry<String, Zone> zoneEntry : zonesMap.entrySet()) {
            TxsChart zoneChart = new TxsChart(zoneEntry.getKey());
            for (Map.Entry<LocalDateTime, List<Zone.Time>> timeEntry : zoneEntry.getValue().timeMap.entrySet()) {
                BigInteger txsCount = BigInteger.ZERO;
                for (Zone.Time time : timeEntry.getValue()) {
                    txsCount = txsCount.add(time.getTxCount());
                }
                TxsChart.Data.ChartItem chartItem = new TxsChart.Data.ChartItem();
                chartItem.setTime(timeEntry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setValue(txsCount);
                zoneChart.getData().getChart().add(chartItem);
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

        public Zone(Tx tx) {
            this.zone = tx.getId().getZone();
            this.timeMap = new TreeMap<>();
            addTime(tx);
        }

        public void addTime(Tx tx) {
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
            BigInteger txCount;

            public Time(Tx tx) {
                this.dateTime = tx.getId().getDatetime();
                this.txCount = BigInteger.valueOf(tx.getTxsCount());
                //addTx(tx);
            }

//            public void addTx(Tx tx) {
//                sumTxs = sumTxs.add(BigDecimal.valueOf(tx.getTxsCount()))
//            }
        }
    }
}
