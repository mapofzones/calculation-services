package com.mapofzones.calculations.services;

import com.mapofzones.calculations.repository.DelegationsChartRepository;
import com.mapofzones.calculations.repository.DelegatorsCountChartRepository;
import com.mapofzones.calculations.repository.IbcTransferChartRepository;
import com.mapofzones.calculations.repository.IbcVolumeChartRepository;
import com.mapofzones.calculations.repository.TxsChartRepository;
import com.mapofzones.calculations.repository.entities.DelegationsAmountChart;
import com.mapofzones.calculations.repository.entities.DelegatorsCountChart;
import com.mapofzones.calculations.repository.entities.IbcTransferChart;
import com.mapofzones.calculations.repository.entities.IbcVolumeChart;
import com.mapofzones.calculations.repository.entities.TxsChart;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

@Service
public class ChartService implements IChartService {

    private final DelegationsChartRepository delegationsChartRepository;
    private final DelegatorsCountChartRepository delegatorsCountChartRepository;
    private final IbcTransferChartRepository ibcTransferChartRepository;
    private final IbcVolumeChartRepository ibcVolumeChartRepository;
    private final TxsChartRepository txsChartRepository;

    public ChartService(DelegationsChartRepository delegationsChartRepository,
                        DelegatorsCountChartRepository delegatorsCountChartRepository,
                        IbcTransferChartRepository ibcTransferChartRepository,
                        IbcVolumeChartRepository ibcVolumeChartRepository,
                        TxsChartRepository txsChartRepository) {
        this.delegationsChartRepository = delegationsChartRepository;
        this.delegatorsCountChartRepository = delegatorsCountChartRepository;
        this.ibcTransferChartRepository = ibcTransferChartRepository;
        this.ibcVolumeChartRepository = ibcVolumeChartRepository;
        this.txsChartRepository = txsChartRepository;
    }

    @Override
    public DelegationsAmountChart findDelegationAmountChart(String zone, String period) {
        return delegationsChartRepository.findByData_Zone(zone).withPeriod(fromTime(period));
    }

    @Override
    public DelegatorsCountChart findDelegatorsCountChart(String zone, String period) {
        return delegatorsCountChartRepository.findByData_Zone(zone).withPeriod(fromTime(period));
    }

    @Override
    public IbcTransferChart findIbcTransferChart(String zone, String period) {
        return ibcTransferChartRepository.findByData_Zone(zone).withPeriod(fromTime(period));
    }

    @Override
    public IbcVolumeChart findIbcVolumeChart(String zone, String period) {
        IbcVolumeChart ibcVolumeChart = ibcVolumeChartRepository.findByData_Zone(zone).withPeriod(fromTime(period));

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
        TxsChart txsChart = txsChartRepository.findByData_Zone(zone).withPeriod(fromTime(period));
        Integer totalTxsCount = txsChart.getData().getChart().stream().map(TxsChart.Data.ChartItem::getTxsCount).reduce(0, Integer::sum);
        txsChart.getData().setTotalTxsCount(totalTxsCount);
        return txsChart;
    }

    private Long fromTime(String period) {
        return LocalDateTime.now().minus(DurationStyle.SIMPLE.parse(period)).toEpochSecond(UTC);
    }

}
