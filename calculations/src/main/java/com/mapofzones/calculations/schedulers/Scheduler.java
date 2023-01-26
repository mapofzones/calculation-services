package com.mapofzones.calculations.schedulers;

import com.mapofzones.calculations.activeaddresses.service.ActiveAddressesService;
import com.mapofzones.calculations.delegations.service.DelegationsService;
import com.mapofzones.calculations.delegators.service.DelegatorsService;
import com.mapofzones.calculations.ibctransfers.services.IbcTransferService;
import com.mapofzones.calculations.ibcvolume.services.IbcVolumeService;
import com.mapofzones.calculations.transactions.services.TxsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    private final DelegationsService delegationsService;
    private final DelegatorsService delegatorsService;
    private final IbcVolumeService ibcVolumeService;
    private final TxsService txsService;
    private final IbcTransferService ibcTransferService;
    private final ActiveAddressesService activeAddressesService;

    public Scheduler(DelegationsService delegationsService,
                     DelegatorsService delegatorsService,
                     IbcVolumeService ibcVolumeService,
                     TxsService txsService,
                     IbcTransferService ibcTransferService,
                     ActiveAddressesService activeAddressesService) {
        this.delegationsService = delegationsService;
        this.delegatorsService = delegatorsService;
        this.ibcVolumeService = ibcVolumeService;
        this.txsService = txsService;
        this.ibcTransferService = ibcTransferService;
        this.activeAddressesService = activeAddressesService;
    }


//    @Scheduled(cron = "0 55 * * * *")
    @Scheduled(fixedDelayString = "3600000", initialDelay = 10000)
    public void delegationsCalculation() {
        log.info("Start: delegationsCalculation");
        delegationsService.doCalculation();
        log.info("Finish: delegationsCalculation");
    }

    //    @Scheduled(cron = "0 55 * * * *")
    @Scheduled(fixedDelayString = "3600000", initialDelay = 10000)
    public void delegatorsCalculation() {
        log.info("Start: delegationsCalculation");
        delegatorsService.doCalculation();
        log.info("Finish: delegationsCalculation");
    }

    @Scheduled(fixedDelayString = "3600000", initialDelay = 10000)
    public void ibcVolumeCalculation() {
        log.info("Start: ibcVolumeCalculation");
        ibcVolumeService.doCalculation();
        log.info("Finish: ibcVolumeCalculation");
    }

    @Scheduled(fixedDelayString = "3600000", initialDelay = 1000)
    public void txsCountCalculation() {
        log.info("Start: txsCountCalculation");
        txsService.doCalculation();
        log.info("Finish: txsCountCalculation");
    }

    @Scheduled(fixedDelayString = "3600000", initialDelay = 1000)
    public void ibcTransfersCountCalculation() {
        log.info("Start: ibcTransfersCountCalculation");
        ibcTransferService.doCalculation();
        log.info("Finish: ibcTransfersCountCalculation");
    }

    @Scheduled(fixedDelayString = "3600000", initialDelay = 1000)
    public void activeAddressesCalculation() {
        log.info("Start: activeAddressesCalculation");
        activeAddressesService.doCalculation();
        log.info("Finish: activeAddressesCalculation");
    }
}
