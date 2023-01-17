package com.mapofzones.calculations.delegators.service;

import com.mapofzones.calculations.common.domain.ZoneParameter;
import com.mapofzones.calculations.common.domain.Zone;
import com.mapofzones.calculations.common.postgres.repository.ZoneRepository;
import com.mapofzones.calculations.common.postgres.repository.ZoneParametersRepository;
import com.mapofzones.calculations.delegators.repository.mongo.DelegatorsCountChartRepository;
import com.mapofzones.calculations.delegators.repository.mongo.domain.DelegatorsCountChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.START_HOURS_AGO;

@Service
public class DelegatorsService {

    @Autowired
    private ZoneParametersRepository zoneParametersRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private DelegatorsCountChartRepository delegatorsCountChartRepository;

    public void doCalculation() {
        clear();
        List<Zone> zones = zoneRepository.findAllByIsMainnetTrue();

        for (Zone zone : zones) {
            System.out.println(zone.getChainId());
            doCalculation(zone.getChainId());
        }
    }

    public void doCalculation(String zone) {
        List<ZoneParameter> zoneParametersList = findParametersForLastPeriod(zone, START_HOURS_AGO);
        DelegatorsCountChart delegatorsChart = new DelegatorsCountChart(zone);
        for (ZoneParameter zoneParameter : zoneParametersList) {
            DelegatorsCountChart.Data.Chart chart = new DelegatorsCountChart.Data.Chart();
            chart.setTime(zoneParameter.getZoneParametersId().getDatetime().toEpochSecond(ZoneOffset.UTC));
            chart.setDelegatorsCount(zoneParameter.getDelegatorsCount());
            delegatorsChart.getData().getChart().add(chart);
        }

        update(delegatorsChart);
    }

    @Transactional
    protected void clear() {
        delegatorsCountChartRepository.deleteAll();
    }

    @Transactional
    protected void update(DelegatorsCountChart delegatorsCountChart) {
        System.out.println("Start update");
        delegatorsCountChartRepository.save(delegatorsCountChart);
        System.out.println("Finish update");
    }

    @Transactional("postgresTransactionManager")
    protected List<ZoneParameter> findParametersForLastPeriod(String zone, long hours) {
        return zoneParametersRepository.findAllByZoneForLastPeriod(zone, LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }
}
