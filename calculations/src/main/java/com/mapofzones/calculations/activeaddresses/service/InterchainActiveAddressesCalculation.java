package com.mapofzones.calculations.activeaddresses.service;

import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.InterchainActiveAddressesChart;
import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.InterchainActiveAddressesStats;
import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddressesResultMapping;

import java.time.ZoneOffset;
import java.util.List;

public class InterchainActiveAddressesCalculation {

    private InterchainActiveAddressesCalculation(){}

    public static InterchainActiveAddressesChart buildChart(List<ActiveAddressesResultMapping> activeAddressesResultMappingList) {
        InterchainActiveAddressesChart chart = new InterchainActiveAddressesChart();
        for (ActiveAddressesResultMapping activeAddressesResultMapping : activeAddressesResultMappingList) {
            InterchainActiveAddressesChart.Data.ChartItem chartItem = new InterchainActiveAddressesChart.Data.ChartItem(activeAddressesResultMapping.getDatetime().toEpochSecond(ZoneOffset.UTC));
            chartItem.setInterchainActiveAddressesCount(activeAddressesResultMapping.getActiveAddressesCount());
            chart.getData().getChart().add(chartItem);
        }
        return chart;
    }

    public static InterchainActiveAddressesStats buildStats(Integer totalUniqueActiveAddressesCountDay,
                                                            Integer totalUniqueActiveAddressesCountWeek,
                                                            Integer totalUniqueActiveAddressesCountMonth) {
        InterchainActiveAddressesStats stats = new InterchainActiveAddressesStats();
        stats.getData().setTotalInterchainUniqueActiveAddressesDay(totalUniqueActiveAddressesCountDay);
        stats.getData().setTotalInterchainUniqueActiveAddressesWeek(totalUniqueActiveAddressesCountWeek);
        stats.getData().setTotalInterchainUniqueActiveAddressesMonth(totalUniqueActiveAddressesCountMonth);
        return stats;
    }

}
