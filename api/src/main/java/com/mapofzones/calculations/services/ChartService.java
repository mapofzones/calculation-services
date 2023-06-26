package com.mapofzones.calculations.services;

import com.mapofzones.calculations.repository.ActiveAddressesCountChartRepository;
import com.mapofzones.calculations.repository.ActiveAddressesCountStatsRepository;
import com.mapofzones.calculations.repository.DelegationsChartRepository;
import com.mapofzones.calculations.repository.DelegatorsCountChartRepository;
import com.mapofzones.calculations.repository.IbcTransferChartRepository;
import com.mapofzones.calculations.repository.IbcVolumeChartRepository;
import com.mapofzones.calculations.repository.InterchainActiveAddressesCountStatsRepository;
import com.mapofzones.calculations.repository.TxsChartRepository;
import com.mapofzones.calculations.repository.entities.ActiveAddressesCountChart;
import com.mapofzones.calculations.repository.entities.ActiveAddressesCountStats;
import com.mapofzones.calculations.repository.entities.DelegationsAmountChart;
import com.mapofzones.calculations.repository.entities.DelegatorsCountChart;
import com.mapofzones.calculations.repository.entities.IbcTransferChart;
import com.mapofzones.calculations.repository.entities.IbcVolumeChart;
import com.mapofzones.calculations.repository.entities.InterchainActiveAddressesCountStats;
import com.mapofzones.calculations.repository.entities.TxsChart;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Service
public class ChartService implements IChartService {

    private final DelegationsChartRepository delegationsChartRepository;
    private final DelegatorsCountChartRepository delegatorsCountChartRepository;
    private final IbcTransferChartRepository ibcTransferChartRepository;
    private final IbcVolumeChartRepository ibcVolumeChartRepository;
    private final TxsChartRepository txsChartRepository;
    private final ActiveAddressesCountChartRepository activeAddressesCountChartRepository;
    private final ActiveAddressesCountStatsRepository activeAddressesCountStatsRepository;
    private final InterchainActiveAddressesCountStatsRepository interchainActiveAddressesCountStatsRepository;

    public ChartService(DelegationsChartRepository delegationsChartRepository,
                        DelegatorsCountChartRepository delegatorsCountChartRepository,
                        IbcTransferChartRepository ibcTransferChartRepository,
                        IbcVolumeChartRepository ibcVolumeChartRepository,
                        TxsChartRepository txsChartRepository,
                        ActiveAddressesCountChartRepository activeAddressesCountChartRepository,
                        ActiveAddressesCountStatsRepository activeAddressesCountStatsRepository,
                        InterchainActiveAddressesCountStatsRepository interchainActiveAddressesCountStatsRepository) {
        this.delegationsChartRepository = delegationsChartRepository;
        this.delegatorsCountChartRepository = delegatorsCountChartRepository;
        this.ibcTransferChartRepository = ibcTransferChartRepository;
        this.ibcVolumeChartRepository = ibcVolumeChartRepository;
        this.txsChartRepository = txsChartRepository;
        this.activeAddressesCountChartRepository = activeAddressesCountChartRepository;
        this.activeAddressesCountStatsRepository = activeAddressesCountStatsRepository;
        this.interchainActiveAddressesCountStatsRepository = interchainActiveAddressesCountStatsRepository;
    }

    @Override
    public DelegationsAmountChart findDelegationAmountChart(String zone, String period) {
        return delegationsChartRepository.findByData_Zone(zone).withPeriod(toTime(period));
    }

    @Override
    public DelegatorsCountChart findDelegatorsCountChart(String zone, String period) {
        DelegatorsCountChart delegatorsCountChart = delegatorsCountChartRepository.findByData_Zone(zone, -toDays(period) - 1);
        delegatorsCountChart.getData().setTotalDelegatorsCount(
                delegatorsCountChart.getData().getChart().get(delegatorsCountChart.getData().chart.size()-1).getDelegatorsCount()
        );
        return delegatorsCountChart;
    }

    @Override
    public List<DelegatorsCountChart> findBulkDelegatorsCountChart(List<String> zones, String period) {
        List<DelegatorsCountChart> delegatorsCountCharts = new ArrayList<>();

        for (String zone : zones)
            delegatorsCountCharts.add(findDelegatorsCountChart(zone, period));

        return delegatorsCountCharts;
    }

    @Override
    public IbcTransferChart findIbcTransferChart(String zone, String period) {
        IbcTransferChart ibcTransferChart = ibcTransferChartRepository.findByZoneAndPeriod(zone, -toHours(period));

        BigInteger totalIbsTransfers = ibcTransferChart.getData().getChart().stream().map(IbcTransferChart.Data.ChartItem::getIbcTransfersCount).reduce(BigInteger.ZERO, BigInteger::add);
        BigInteger totalPending = ibcTransferChart.getData().getChart().stream().map(IbcTransferChart.Data.ChartItem::getPending).reduce(BigInteger.ZERO, BigInteger::add);

        ibcTransferChart.getData().setTotalIbcTransfersCount(totalIbsTransfers);
        ibcTransferChart.getData().setTotalPending(totalPending);

        return ibcTransferChart;
    }

    @Override
    public IbcVolumeChart findIbcVolumeChart(String zone, String period) {
        IbcVolumeChart ibcVolumeChart = ibcVolumeChartRepository.findByData_Zone(zone).withPeriod(toTime(period));

        BigDecimal totalIbcIn = ibcVolumeChart.getData().getChart().stream().map(IbcVolumeChart.Data.ChartItem::getIbcIn).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalIbcOut = ibcVolumeChart.getData().getChart().stream().map(IbcVolumeChart.Data.ChartItem::getIbcOut).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalIbc = ibcVolumeChart.getData().getChart().stream().map(IbcVolumeChart.Data.ChartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        ibcVolumeChart.getData().setTotalIbcIn(totalIbcIn);
        ibcVolumeChart.getData().setTotalIbcOut(totalIbcOut);
        ibcVolumeChart.getData().setTotalIbc(totalIbc);

        return ibcVolumeChart;
    }

    @Override
    public TxsChart findTxsChart(String zone, String period) {
        TxsChart txsChart = txsChartRepository.findByData_Zone(zone, -toDays(period) - 1);
        Integer totalTxsCount = txsChart.getData().getChart()
                .stream().limit(txsChart.getData().getChart().size()-1)
                .map(TxsChart.Data.ChartItem::getTxsCount).reduce(0, Integer::sum);
        txsChart.getData().setTotalTxsCount(totalTxsCount);
        return txsChart;
    }

    @Override
    public List<TxsChart> findBulkTxsChart(List<String> zones, String period) {
        List<TxsChart> txsCharts = new ArrayList<>();

        for (String zone : zones)
            txsCharts.add(findTxsChart(zone, period));

        return txsCharts;
    }

    @Override
    public ActiveAddressesCountChart findActiveAddressesCountChart(String zone, String period) {
        Long periodInDays = -toDays(period)-1;
        ActiveAddressesCountChart chart = activeAddressesCountChartRepository.findByZoneAndPeriod(zone, periodInDays);
        //ActiveAddressesCountStats stats = activeAddressesCountStatsRepository.findByData_Zone(zone);
        //chart.setTotalActiveAddressesCountStats(stats, periodInHours);
        return chart;
    }

    @Override
    public List<ActiveAddressesCountChart> findBulkActiveAddressesCountChart(List<String> zones, String period) {
        List<ActiveAddressesCountChart> activeAddressesCountCharts = new ArrayList<>();

        for (String zone : zones)
            activeAddressesCountCharts.add(findActiveAddressesCountChart(zone, period));

        return activeAddressesCountCharts;

    }

    @Override
    public ActiveAddressesCountStats findActiveAddressesCountStats(String zone) {
        return activeAddressesCountStatsRepository.findByData_Zone(zone);
    }

    @Override
    public InterchainActiveAddressesCountStats findInterchainActiveAddressesCountStats() {
        return interchainActiveAddressesCountStatsRepository.findAll().get(0);
    }

    private Long toTime(String period) {
        return LocalDateTime.now().minus(DurationStyle.SIMPLE.parse(period)).toEpochSecond(UTC);
    }

    private Long toHours(String period) {
        return DurationStyle.SIMPLE.parse(period).toHours();
    }

    private Long toDays(String period) {
        return DurationStyle.SIMPLE.parse(period).toDays();
    }
}
