package com.mapofzones.calculations.delegators.service;

import com.mapofzones.calculations.common.domain.ZoneParameter;
import com.mapofzones.calculations.common.postgres.repository.ZoneParametersRepository;
import com.mapofzones.calculations.delegators.repository.mongo.DelegatorsCountChartRepository;
import com.mapofzones.calculations.delegators.repository.mongo.domain.DelegatorsCountChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_MONTH;

@Service
public class DelegatorsService {

    @Autowired
    private ZoneParametersRepository zoneParametersRepository;

    @Autowired
    private DelegatorsCountChartRepository delegatorsCountChartRepository;

    public void doCalculation() {
        List<ZoneParameter.DelegatorsCountMapping> zoneParametersList = findParametersForLastPeriod(HOURS_IN_MONTH);
        List<DelegatorsCountChart> delegatorsChart = DelegatorsCalculation.buildChart(zoneParametersList);

        for (DelegatorsCountChart currentChart : delegatorsChart) {
            boolean nullIsExists = currentChart.getData().getChart().stream().anyMatch(chartItem -> chartItem.delegatorsCount == null);
            if (nullIsExists) {
                for (int i = 1; i < currentChart.getData().getChart().size(); i++) {
                    if (currentChart.getData().getChart().get(i).getDelegatorsCount() == null) {
                        currentChart.getData().getChart().get(i).setDelegatorsCount(currentChart.getData().getChart().get(i-1).getDelegatorsCount());
                    }
                }
            }
        }
        update(delegatorsChart);
    }

    @Transactional
    protected void clear() {
        delegatorsCountChartRepository.deleteAll();
    }

    @Transactional
    protected void update(List<DelegatorsCountChart> delegatorsCountChart) {
        clear();
        System.out.println("Start update");
        delegatorsCountChartRepository.saveAll(delegatorsCountChart);
        System.out.println("Finish update");
    }

    @Transactional("postgresTransactionManager")
    protected List<ZoneParameter.DelegatorsCountMapping> findParametersForLastPeriod(long hours) {
        return zoneParametersRepository.getDelegatorsCount(LocalDateTime.now().minus(hours, ChronoUnit.HOURS),  LocalDateTime.now());
    }
}
