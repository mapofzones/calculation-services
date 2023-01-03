package com.mapofzones.calculations.ibcvolume.services;

import com.mapofzones.calculations.ibcvolume.repository.mongo.domain.IbcVolumeChart;
import com.mapofzones.calculations.ibcvolume.repository.postgres.domain.CashFlowToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.mapofzones.calculations.common.constants.CommonConst.START_HOURS_AGO;

public class IbcVolumeCalculation {

    private IbcVolumeCalculation(){}

    public static List<IbcVolumeChart> buildChart(List<CashFlowToken> cashFlowTokenList) {
        IbcVolumeCalculation calculation = new IbcVolumeCalculation();
        Map<String, Zone> zonesMap = new TreeMap<>();

        for (CashFlowToken cashFlowToken : cashFlowTokenList) {
            if (zonesMap.containsKey(cashFlowToken.getZone())) {
                zonesMap.get(cashFlowToken.getZone()).addTime(cashFlowToken);
            } else {
                Zone zone = new Zone(cashFlowToken);
                zonesMap.put(zone.getZone(), zone);
            }
        }

        List<IbcVolumeChart> allZonesChart = new ArrayList<>();

        for (Map.Entry<String, Zone> zone : zonesMap.entrySet()) {
            IbcVolumeChart zoneChart = new IbcVolumeChart(zone.getKey());

            for (Map.Entry<LocalDateTime, List<Time>> entry : zone.getValue().timeMap.entrySet()) {


                BigDecimal in = BigDecimal.ZERO;
                BigDecimal out = BigDecimal.ZERO;
                for (Time time : entry.getValue()) {
                    in = in.add(time.sumIn);
                    out = out.add(time.sumOut);
                }

                IbcVolumeChart.Data.ChartItem chartItem = new IbcVolumeChart.Data.ChartItem();
                chartItem.setTime(entry.getKey().toEpochSecond(ZoneOffset.UTC));
                chartItem.setTime1(entry.getKey());
                chartItem.setIbcIn(in);
                chartItem.setIbcOut(out);
                chartItem.setTotal(in.add(out));
                zoneChart.getData().getChart().add(chartItem);
            }

            calculation.fillGaps(zoneChart);


            allZonesChart.add(zoneChart);
        }

        return allZonesChart;
    }

    private void fillGaps(IbcVolumeChart chart) {
        LocalDateTime current = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minus(START_HOURS_AGO, ChronoUnit.HOURS);
        LocalDateTime end = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        while (current.isBefore(end) || current.isEqual(end)) {
            chart.addEmptyChartItem(current.toEpochSecond(ZoneOffset.UTC));
            current = current.plus(1, ChronoUnit.HOURS);
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"zone"})
    private static class Zone {
        String zone;
        Map<LocalDateTime, List<Time>> timeMap;

        public Zone(CashFlowToken cashFlowToken) {
            this.zone = cashFlowToken.getZone();
            this.timeMap = new TreeMap<>();
            addTime(cashFlowToken);
        }

        public void addTime(CashFlowToken cashFlowToken) {
            Time time = new Time(cashFlowToken);

            if (timeMap.containsKey(time.dateTime)) {
                timeMap.get(time.dateTime).add(time);
            } else {
                List<Time> timeList = new ArrayList<>();
                timeList.add(time);
                timeMap.put(time.dateTime, timeList);
            }
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"dateTime"})
    private static class Time {

        LocalDateTime dateTime;

        BigDecimal sumIn = BigDecimal.ZERO;
        BigDecimal sumOut = BigDecimal.ZERO;

        Flow flowIn;
        Flow flowOut;

        public Time(CashFlowToken cashFlowToken) {
            this.dateTime = cashFlowToken.getHour();
            addFlow(cashFlowToken);
        }

        public void addFlow(CashFlowToken cashFlowToken) {
            Flow flow = new Flow(cashFlowToken);
            if (cashFlowToken.getZone().equals(cashFlowToken.getZoneSource())) {
                sumOut = sumOut.add(flow.sum);
                flowOut = flow;
            } else {
                sumIn = sumIn.add(flow.sum);
                flowIn = flow;
            }
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"src", "dest"})
    public static class Flow {

        private String src;
        private String dest;
        private BigDecimal amount;
        private Integer exponent;
        private BigDecimal coingeckoTokenPrice;
        private BigDecimal osmosisTokenPrice;
        private BigDecimal sifchainTokenPrice;
        private BigDecimal sum = BigDecimal.ZERO;

        public Flow(CashFlowToken cashFlowToken) {
            this.src = cashFlowToken.getZoneSource();
            this.dest = cashFlowToken.getZoneDestination();
            this.amount = cashFlowToken.getAmount();
            this.exponent = cashFlowToken.getExponent();
            this.coingeckoTokenPrice = cashFlowToken.getCoingeckoTokenPrice();
            this.osmosisTokenPrice = cashFlowToken.getOsmosisTokenPrice();
            this.sifchainTokenPrice = cashFlowToken.getSifchainTokenPrice();

            if (exponent != null) {
                if (coingeckoTokenPrice != null) {
                    this.sum = amount.divide(BigDecimal.valueOf(Math.pow(10, exponent))).multiply(coingeckoTokenPrice);
                } else if (osmosisTokenPrice != null) {
                    this.sum = amount.divide(BigDecimal.valueOf(Math.pow(10, exponent))).multiply(osmosisTokenPrice);
                } else if (sifchainTokenPrice != null) {
                    this.sum = amount.divide(BigDecimal.valueOf(Math.pow(10, exponent))).multiply(sifchainTokenPrice);
                }
            }


        }
    }
}
