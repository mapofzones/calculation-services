package com.mapofzones.calculations.transactions.services;


import com.mapofzones.calculations.transactions.repository.mongo.TxsChartRepository;
import com.mapofzones.calculations.transactions.repository.mongo.domain.TxsChart;
import com.mapofzones.calculations.transactions.repository.postgres.TxsRepository;
import com.mapofzones.calculations.transactions.repository.postgres.domain.Tx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.START_HOURS_AGO;

@Service
public class TxsService {

    @Autowired
    private TxsChartRepository txsChartRepository;

    @Autowired
    private TxsRepository txsRepository;


    public void doCalculation() {
        List<Tx> txList = findAllForLastPeriod(START_HOURS_AGO);
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
    protected List<Tx> findAllForLastPeriod(long hours) {
        try {
            return txsRepository.findAllForLastPeriod(LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
