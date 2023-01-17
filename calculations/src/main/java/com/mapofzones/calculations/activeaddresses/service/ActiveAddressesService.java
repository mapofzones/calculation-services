package com.mapofzones.calculations.activeaddresses.service;

import com.mapofzones.calculations.activeaddresses.repository.mongo.ActiveAddressesChartRepository;
import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesChart;
import com.mapofzones.calculations.activeaddresses.repository.postgres.ActiveAddressesRepository;
import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.START_HOURS_AGO;

@Service
public class ActiveAddressesService {

    @Autowired
    private ActiveAddressesRepository activeAddressesRepository;

    @Autowired
    private ActiveAddressesChartRepository chartRepository;

    public void doCalculation() {
        List<ActiveAddress> activeAddresses = findAllForLastPeriod(START_HOURS_AGO);
        List<ActiveAddressesChart> activeAddressesChart = ActiveAddressesCalculation.buildChart(activeAddresses);
        update(activeAddressesChart);
    }

    @Transactional
    protected void update(List<ActiveAddressesChart> activeAddressesChart) {
        clear();
        chartRepository.saveAll(activeAddressesChart);
    }

    @Transactional
    protected void clear() {
        chartRepository.deleteAll();
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddress> findAllForLastPeriod(long hours) {
        return activeAddressesRepository.findAllByZoneForLastPeriod(LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }

}
