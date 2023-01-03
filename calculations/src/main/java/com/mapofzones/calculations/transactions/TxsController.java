//package com.mapofzones.calculations.transactions;
//
//import com.mapofzones.calcutalionservice.ibcvolume.repository.mongo.IbcVolumeChartRepository;
//import com.mapofzones.calcutalionservice.ibcvolume.repository.mongo.domain.IbcVolumeChart;
//import com.mapofzones.calcutalionservice.ibcvolume.services.IbcVolumeService;
//import com.mapofzones.calcutalionservice.transactions.repository.mongo.TxsChartRepository;
//import com.mapofzones.calcutalionservice.transactions.repository.mongo.domain.TxsChart;
//import com.mapofzones.calcutalionservice.transactions.services.TxsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.convert.DurationStyle;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//
//import static java.time.ZoneOffset.UTC;
//
//@RestController
//public class TxsController {
//
//    @Autowired
//    private TxsService txsService;
//
//    @Autowired
//    private TxsChartRepository chartRepository;
//
//    @GetMapping("/api/txsCount/doCalculation")
//    public void doCalculation() {
//        txsService.doCalculation();
//    }
//
//
//    @GetMapping("/api/txsCount/{zone}/{period}")
//    public ResponseEntity<?> findByZone(@PathVariable String zone, @PathVariable String period) {
//
//        Long fromTime = LocalDateTime.now().minus(DurationStyle.SIMPLE.parse(period)).toEpochSecond(UTC);
//        TxsChart chart = chartRepository.findByData_Zone(zone);
//        return new ResponseEntity<>(chart.withPeriod(fromTime), HttpStatus.OK);
//    }
//
//}
