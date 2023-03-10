package com.mapofzones.calculations.transactions.services;


import com.mapofzones.calculations.transactions.repository.mongo.TxsChartRepository;
import com.mapofzones.calculations.transactions.repository.mongo.domain.TxsChart;
import com.mapofzones.calculations.transactions.repository.postgres.TxsRepository;
import com.mapofzones.calculations.transactions.repository.postgres.domain.CustomTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_MONTH;

@Service
public class TxsService {

    @Autowired
    private TxsChartRepository txsChartRepository;

    @Autowired
    private TxsRepository txsRepository;

    public void doCalculation() {
        List<CustomTx> txList = findAllForLastPeriod(HOURS_IN_MONTH);
        List<TxsChart> txsCharts = TxsCalculation.buildChart(txList);
        update(txsCharts);
    }

    @Transactional
    protected void update(List<TxsChart> txsChart) {
        clear();
        System.out.println("Start update");
        txsChartRepository.saveAll(txsChart);
        System.out.println("Finish update");
    }

    @Transactional
    protected void clear() {
        txsChartRepository.deleteAll();
    }

    @Transactional("postgresTransactionManager")
    protected List<CustomTx> findAllForLastPeriod(long hours) {
        try {
            return txsRepository.getTxCount(LocalDateTime.now().minus(hours, ChronoUnit.HOURS), LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
