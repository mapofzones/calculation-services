package com.mapofzones.calculations.delegationamount.service;

import com.mapofzones.calculations.common.repository.postgres.domain.Token;
import com.mapofzones.calculations.common.repository.postgres.domain.TokensRepository;
import com.mapofzones.calculations.common.repository.postgres.domain.Zone;
import com.mapofzones.calculations.common.repository.postgres.domain.ZoneRepository;
import com.mapofzones.calculations.delegationamount.repository.mongo.MongoChartRepository;
import com.mapofzones.calculations.delegationamount.repository.mongo.domain.DelegationsAmountChart;
import com.mapofzones.calculations.delegationamount.repository.postgres.ZoneParametersRepository;
import com.mapofzones.calculations.delegationamount.repository.postgres.domain.ZoneParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.START_HOURS_AGO;

@Service
public class DelegationsAmountService {

    @Autowired
    private ZoneParametersRepository zoneParametersRepository;

    @Autowired
    private TokensRepository tokensRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private MongoChartRepository mongoChartRepository;

    public void doCalculation() {
        clear();
        List<Zone> zones = zoneRepository.findAllByIsMainnetTrue();

        for (Zone zone : zones) {
            System.out.println(zone.getChainId());
            doCalculation(zone.getChainId());
        }
    }

    public void doCalculation(String zone) {
        List<ZoneParameter> zoneParametersList = findDelegationsAmountForLastPeriod(zone, START_HOURS_AGO);
        Token token = findMainTokenByZone(zone);

        DelegationsAmountChart delegationsAmountChart = new DelegationsAmountChart(zone);


        for (ZoneParameter zoneParameter : zoneParametersList) {

            if (!(zoneParameter.getDelegationAmount() == null || zoneParameter.getDelegationAmount().equals(BigDecimal.ZERO)) && !(token == null || token.getExponent() == null)) {
                DelegationsAmountChart.Data.Chart chart = new DelegationsAmountChart.Data.Chart();
                chart.setTime(zoneParameter.getZoneParametersId().getDatetime().toEpochSecond(ZoneOffset.UTC));
                chart.setValue(zoneParameter.getDelegationAmount().divide(BigDecimal.valueOf(Math.pow(10, token.getExponent()))));
                delegationsAmountChart.getData().getChart().add(chart);
            }
        }

        update(delegationsAmountChart);
    }

    @Transactional
    protected void clear() {
        mongoChartRepository.deleteAll();
    }

    @Transactional
    protected void update(DelegationsAmountChart delegationsAmountChart) {
        System.out.println("Start update");
        mongoChartRepository.save(delegationsAmountChart);
        System.out.println("Finish update");
    }

    @Transactional("postgresTransactionManager")
    protected List<ZoneParameter> findDelegationsAmountForLastPeriod(String zone, long hours) {
        return zoneParametersRepository.findAllByZoneForLastPeriod(zone, LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected Token findMainTokenByZone(String zone) {
       return tokensRepository.findMainTokenByZone(zone);
    }

}
