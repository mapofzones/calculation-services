package com.mapofzones.calculations.services;

import com.mapofzones.calculations.repository.entities.DelegationsAmountChart;
import com.mapofzones.calculations.repository.entities.IbcTransferChart;
import com.mapofzones.calculations.repository.entities.IbcVolumeChart;
import com.mapofzones.calculations.repository.entities.TxsChart;

public interface IChartService {

    DelegationsAmountChart findDelegationAmountChart(String zone, String period);
    IbcTransferChart findIbcTransferChart(String zone, String period);
    IbcVolumeChart findIbcVolumeChart(String zone, String period);
    TxsChart findTxsChart(String zone, String period);

}
