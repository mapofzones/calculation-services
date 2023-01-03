package com.mapofzones.calculations.ibcvolume.services;

import com.mapofzones.calculations.ibcvolume.repository.mongo.IbcVolumeChartRepository;
import com.mapofzones.calculations.ibcvolume.repository.mongo.domain.IbcVolumeChart;
import com.mapofzones.calculations.ibcvolume.repository.postgres.CashFlowRepository;
import com.mapofzones.calculations.ibcvolume.repository.postgres.domain.CashFlowToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.START_HOURS_AGO;

@Service
public class IbcVolumeService {

    @Autowired
    private CashFlowRepository cashFlowRepository;

    @Autowired
    private IbcVolumeChartRepository chartRepository;


    public void doCalculation() {
        List<CashFlowToken> cashFlowTokenList = findAllForLastPeriod(START_HOURS_AGO);
        List<IbcVolumeChart> ibcVolumeChart = IbcVolumeCalculation.buildChart(cashFlowTokenList);
        update(ibcVolumeChart);
    }

    @Transactional
    protected void update(List<IbcVolumeChart> ibcVolumeChart) {
        clear();
        System.out.println("Start update");
        chartRepository.saveAll(ibcVolumeChart);
        System.out.println("Finish update");
    }

    @Transactional
    protected void clear() {
        chartRepository.deleteAll();
    }


    @Transactional("postgresTransactionManager")
    protected List<CashFlowToken> findAllForLastPeriod(long hours) {
        return cashFlowRepository.findAllForLastPeriod(LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }
}
