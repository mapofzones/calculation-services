package com.mapofzones.calculations.delegations.service;

import com.mapofzones.calculations.common.domain.Token;
import com.mapofzones.calculations.common.domain.Zone;
import com.mapofzones.calculations.common.domain.ZoneParameter;
import com.mapofzones.calculations.common.postgres.repository.ZoneRepository;
import com.mapofzones.calculations.common.postgres.repository.TokensRepository;
import com.mapofzones.calculations.common.postgres.repository.ZoneParametersRepository;
import com.mapofzones.calculations.delegations.repository.mongo.MongoChartRepository;
import com.mapofzones.calculations.delegations.repository.mongo.domain.DelegationsChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mapofzones.calculations.common.constants.CommonConst.HOURS_IN_MONTH;

@Service
public class DelegationsService {

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
        List<ZoneParameter> zoneParametersList = findParametersForLastPeriod(zone, HOURS_IN_MONTH + 1);
        Token token = findMainTokenByZone(zone);

        DelegationsChart delegationsChart = new DelegationsChart(zone);


        for (ZoneParameter zoneParameter : zoneParametersList) {

            if (!(zoneParameter.getDelegationAmount() == null || zoneParameter.getDelegationAmount().equals(BigDecimal.ZERO)
                    || zoneParameter.getUndelegationAmount() == null || zoneParameter.getUndelegationAmount().equals(BigDecimal.ZERO))
                    && !(token == null || token.getExponent() == null)) {
                DelegationsChart.Data.Chart chart = new DelegationsChart.Data.Chart();
                chart.setTime(zoneParameter.getZoneParametersId().getDatetime().toEpochSecond(ZoneOffset.UTC));
                chart.setDelegationAmount(zoneParameter.getDelegationAmount().divide(BigDecimal.valueOf(Math.pow(10, token.getExponent()))));
                chart.setUndelegationAmount(zoneParameter.getUndelegationAmount().divide(BigDecimal.valueOf(Math.pow(10, token.getExponent()))));
                delegationsChart.getData().getChart().add(chart);
            }
        }

        update(delegationsChart);
    }

    @Transactional
    protected void clear() {
        mongoChartRepository.deleteAll();
    }

    @Transactional
    protected void update(DelegationsChart delegationsChart) {
        System.out.println("Start update");
        mongoChartRepository.save(delegationsChart);
        System.out.println("Finish update");
    }

    @Transactional("postgresTransactionManager")
    protected List<ZoneParameter> findParametersForLastPeriod(String zone, long hours) {
        return zoneParametersRepository.findAllByZoneForLastPeriod(zone, LocalDateTime.now().minus(hours, ChronoUnit.HOURS));
    }

    @Transactional("postgresTransactionManager")
    protected Token findMainTokenByZone(String zone) {
       return tokensRepository.findMainTokenByZone(zone);
    }

}
