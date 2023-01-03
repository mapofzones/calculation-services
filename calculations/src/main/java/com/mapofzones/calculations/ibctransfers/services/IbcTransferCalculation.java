package com.mapofzones.calculations.ibctransfers.services;

import com.mapofzones.calculations.ibctransfers.repository.mongo.domain.IbcTransferChart;
import com.mapofzones.calculations.ibctransfers.repository.postgres.domain.IbcTransfer;
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

public class IbcTransferCalculation {

    private IbcTransferCalculation() {}

    public static List<IbcTransferChart> buildChart(List<IbcTransfer> ibcTransferList) {
        Map<String, Zone> zonesMap = new TreeMap<>();
        for (IbcTransfer currentIbcTransfer : ibcTransferList) {
            if (zonesMap.containsKey(currentIbcTransfer.getId().getZone())) {
                zonesMap.get(currentIbcTransfer.getId().getZone()).addTime(currentIbcTransfer);
            } else {
                Zone zone = new Zone(currentIbcTransfer);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<IbcTransferChart> ibcTransfersCharts = new ArrayList<>();


        for (Map.Entry<String, Zone> zoneEntry : zonesMap.entrySet()) {
            IbcTransferChart zoneChart = new IbcTransferChart(zoneEntry.getKey());
            for (Map.Entry<LocalDateTime, List<Zone.Time>> timeEntry : zoneEntry.getValue().timeMap.entrySet()) {
                BigInteger ibcTransfers = BigInteger.ZERO;
                BigInteger pending = BigInteger.ZERO;
                for (Zone.Time time : timeEntry.getValue()) {
                    ibcTransfers = ibcTransfers.add(time.getIbcTransfersCount());
                    pending = pending.add(time.ibcTransfersFailCount);
                }
                IbcTransferChart.Data.ChartItem chartItem = new IbcTransferChart.Data.ChartItem();
                chartItem.setTime(timeEntry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setIbcTransfersCount(ibcTransfers);
                chartItem.setPending(pending);
                zoneChart.getData().getChart().add(chartItem);
            }
            ibcTransfersCharts.add(zoneChart);
        }
        return ibcTransfersCharts;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"zone"})
    private static class Zone {
        String zone;
        Map<LocalDateTime, List<Time>> timeMap;

        public Zone(IbcTransfer ibcTransfer) {
            this.zone = ibcTransfer.getId().getZone();
            this.timeMap = new TreeMap<>();
            addTime(ibcTransfer);
        }

        public void addTime(IbcTransfer ibcTransfer) {
            Time time = new Time(ibcTransfer);

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
            BigInteger ibcTransfersCount;
            BigInteger ibcTransfersFailCount;

            public Time(IbcTransfer ibcTransfer) {
                this.dateTime = ibcTransfer.getId().getDatetime();
                this.ibcTransfersCount = BigInteger.valueOf(ibcTransfer.getIbcTransfersCount());
                this.ibcTransfersFailCount = BigInteger.valueOf(ibcTransfer.getIbcTransfersFailCount());
            }
        }
    }
}
