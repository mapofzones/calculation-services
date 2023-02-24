package com.mapofzones.calculations.activeaddresses.service;

import com.mapofzones.calculations.activeaddresses.repository.mongo.ActiveAddressesChartRepository;
import com.mapofzones.calculations.activeaddresses.repository.mongo.ActiveAddressesStatsRepository;
import com.mapofzones.calculations.activeaddresses.repository.mongo.InterchainActiveAddressesChartRepository;
import com.mapofzones.calculations.activeaddresses.repository.mongo.InterchainActiveAddressesStatsRepository;
import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesChart;
import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.ActiveAddressesStats;
import com.mapofzones.calculations.activeaddresses.repository.mongo.domain.InterchainActiveAddressesStats;
import com.mapofzones.calculations.activeaddresses.repository.postgres.ActiveAddressesRepository;
import com.mapofzones.calculations.activeaddresses.repository.postgres.domain.ActiveAddressesResultMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_DAY;
import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_MONTH;
import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_WEEK;

@Service
public class ActiveAddressesService {

    @Autowired
    private ActiveAddressesRepository activeAddressesRepository;

    @Autowired
    private ActiveAddressesChartRepository activeAddressesChartRepository;

    @Autowired
    private ActiveAddressesStatsRepository activeAddressesStatsRepository;

    @Autowired
    private InterchainActiveAddressesChartRepository interchainActiveAddressesChartRepository;

    @Autowired
    private InterchainActiveAddressesStatsRepository interchainActiveAddressesStatsRepository;

    public void doCalculation() {
        List<ActiveAddressesResultMapping> activeAddressesResultMappingList = findAllActiveAddressesCountForLastPeriod(HOURS_IN_MONTH);

        List<ActiveAddressesChart> activeAddressesChart = ActiveAddressesCalculation.buildChart(activeAddressesResultMappingList);

//        List<ActiveAddressesStats> activeAddressesStats =
//                ActiveAddressesCalculation.buildStats(findTotalActiveAddressesCountDay(), findTotalActiveAddressesCountWeek(), findTotalActiveAddressesCountMonth(),
//                        findTotalIbcActiveAddressesCountDay(), findTotalIbcActiveAddressesCountWeek(), findTotalIbcActiveAddressesCountMonth());


        //InterchainActiveAddressesChart interchainActiveAddressesChart = InterchainActiveAddressesCalculation.buildChart(findAllUniqueActiveAddressesCountForLastPeriod(HOURS_IN_MONTH));
        InterchainActiveAddressesStats interchainActiveAddressesStats = InterchainActiveAddressesCalculation.buildStats(findTotalUniqueActiveAddressesCountDay(),
                findTotalUniqueActiveAddressesCountWeek(), findTotalUniqueActiveAddressesCountMonth());

        update(activeAddressesChart, interchainActiveAddressesStats);
    }

    @Transactional
    protected void update(List<ActiveAddressesChart> activeAddressesChart, InterchainActiveAddressesStats interchainActiveAddressesStats) {
        clear();
        activeAddressesChartRepository.saveAll(activeAddressesChart);
        //activeAddressesStatsRepository.saveAll(activeAddressesStats);
        interchainActiveAddressesStatsRepository.save(interchainActiveAddressesStats);
    }

    @Transactional
    protected void clear() {
        activeAddressesChartRepository.deleteAll();
        //activeAddressesStatsRepository.deleteAll();
        interchainActiveAddressesChartRepository.deleteAll();
        interchainActiveAddressesStatsRepository.deleteAll();

    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findAllActiveAddressesCountForLastPeriod(long hours) {
        System.out.println("findAllActiveAddressesCountForLastPeriod start");
        return activeAddressesRepository.findActiveAddresses(LocalDateTime.now().minus(hours, ChronoUnit.HOURS), LocalDateTime.now());
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findAllUniqueActiveAddressesCountForLastPeriod(long hours) {
        System.out.println("findAllUniqueActiveAddressesCountForLastPeriod start");
        return activeAddressesRepository.findUniqueActiveAddresses(LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findTotalActiveAddressesCountDay() {
        System.out.println("findTotalActiveAddressesCountDay start");
        return activeAddressesRepository.findTotalActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_DAY, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findTotalActiveAddressesCountWeek() {
        System.out.println("findTotalActiveAddressesCountWeek start");
        return activeAddressesRepository.findTotalActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_WEEK, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findTotalActiveAddressesCountMonth() {
        System.out.println("findTotalActiveAddressesCountMonth start");
        return activeAddressesRepository.findTotalActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_MONTH, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findTotalIbcActiveAddressesCountDay() {
        System.out.println("findTotalIbcActiveAddressesCountDay start");
        return activeAddressesRepository.findTotalIbcActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_DAY, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findTotalIbcActiveAddressesCountWeek() {
        System.out.println("findTotalIbcActiveAddressesCountWeek start");
        return activeAddressesRepository.findTotalIbcActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_WEEK, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected List<ActiveAddressesResultMapping> findTotalIbcActiveAddressesCountMonth() {
        System.out.println("findTotalIbcActiveAddressesCountMonth start");
        return activeAddressesRepository.findTotalIbcActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_MONTH, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected Integer findTotalUniqueActiveAddressesCountDay() {
        System.out.println("findTotalUniqueActiveAddressesCountDay start");
        return activeAddressesRepository.findTotalUniqueActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_DAY, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected Integer findTotalUniqueActiveAddressesCountWeek() {
        System.out.println("postgresTransactionManager start");
        return activeAddressesRepository.findTotalUniqueActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_WEEK, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected Integer findTotalUniqueActiveAddressesCountMonth() {
        System.out.println("findTotalUniqueActiveAddressesCountMonth start");
        return activeAddressesRepository.findTotalUniqueActiveAddressesCountByPeriod(LocalDateTime.now().minus(HOURS_IN_MONTH, ChronoUnit.HOURS));
    }

}
