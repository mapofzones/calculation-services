package com.mapofzones.calculations.ibctransfers.services;

import com.mapofzones.calculations.ibctransfers.repository.mongo.IbcTransferChartRepository;
import com.mapofzones.calculations.ibctransfers.repository.mongo.domain.IbcTransferChart;
import com.mapofzones.calculations.ibctransfers.repository.postgres.IbcTransferRepository;
import com.mapofzones.calculations.ibctransfers.repository.postgres.domain.IbcTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_MONTH;

@Service
public class IbcTransferService {

    @Autowired
    private IbcTransferChartRepository ibcTransferChartRepository;

    @Autowired
    private IbcTransferRepository ibcTransferRepository;

    public void doCalculation() {
        List<IbcTransfer> ibcTransferList = findAllForLastPeriod(HOURS_IN_MONTH);
        List<IbcTransferChart> ibcTransferCharts = IbcTransferCalculation.buildChart(ibcTransferList);
        update(ibcTransferCharts);
    }

    @Transactional
    protected void update(List<IbcTransferChart> ibcTransferChart) {
        clear();
        System.out.println("Start update");
        ibcTransferChartRepository.saveAll(ibcTransferChart);
        System.out.println("Finish update");
    }

    @Transactional
    protected void clear() {
        ibcTransferChartRepository.deleteAll();
    }

    @Transactional("postgresTransactionManager")
    protected List<IbcTransfer> findAllForLastPeriod(long hours) {
        return ibcTransferRepository.findAllForLastPeriod(LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }

}
