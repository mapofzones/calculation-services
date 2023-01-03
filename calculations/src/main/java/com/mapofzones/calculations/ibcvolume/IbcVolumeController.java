//package com.mapofzones.calculations.ibcvolume;
//
//import com.mapofzones.calcutalionservice.delegationamount.repository.mongo.domain.DelegationsAmountChart;
//import com.mapofzones.calcutalionservice.ibcvolume.repository.mongo.IbcVolumeChartRepository;
//import com.mapofzones.calcutalionservice.ibcvolume.repository.mongo.domain.IbcVolumeChart;
//import com.mapofzones.calcutalionservice.ibcvolume.services.IbcVolumeService;
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
//public class IbcVolumeController {
//
//    @Autowired
//    private IbcVolumeService ibcVolumeService;
//
//    @Autowired
//    private IbcVolumeChartRepository chartRepository;
//
//    @GetMapping("/api/ibcVolume/doCalculation")
//    public void doCalculation() {
//        ibcVolumeService.doCalculation();
//    }
//
//
//    @GetMapping("/api/ibcVolume/{zone}/{period}")
//    public ResponseEntity<?> findByZone(@PathVariable String zone, @PathVariable String period) {
//
//        Long fromTime = LocalDateTime.now().minus(DurationStyle.SIMPLE.parse(period)).toEpochSecond(UTC);
//        IbcVolumeChart chart = chartRepository.findByData_Zone(zone);
//        return new ResponseEntity<>(chart.withPeriod(fromTime), HttpStatus.OK);
//    }
//
//}
