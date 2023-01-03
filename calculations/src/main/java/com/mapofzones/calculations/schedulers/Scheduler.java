package com.mapofzones.calculations.schedulers;

import com.mapofzones.calculations.delegationamount.service.DelegationsAmountService;
import com.mapofzones.calculations.ibctransfers.services.IbcTransferService;
import com.mapofzones.calculations.ibcvolume.services.IbcVolumeService;
import com.mapofzones.calculations.transactions.services.TxsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

    private final DelegationsAmountService delegationsAmountService;
    private final IbcVolumeService ibcVolumeService;
    private final TxsService txsService;
    private final IbcTransferService ibcTransferService;

    public Scheduler(DelegationsAmountService delegationsAmountService,
                     IbcVolumeService ibcVolumeService,
                     TxsService txsService,
                     IbcTransferService ibcTransferService) {
        this.delegationsAmountService = delegationsAmountService;
        this.ibcVolumeService = ibcVolumeService;
        this.txsService = txsService;
        this.ibcTransferService = ibcTransferService;
    }


//    @Scheduled(cron = "0 55 * * * *")
    @Scheduled(fixedDelayString = "3600000", initialDelay = 10000)
    public void delegationsAmountCalculation() {
        log.info("Start: delegationsAmountCalculation");
        delegationsAmountService.doCalculation();
        log.info("Finish: delegationsAmountCalculation");
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
}
