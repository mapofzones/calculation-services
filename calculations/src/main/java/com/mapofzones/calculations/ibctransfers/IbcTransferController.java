//package com.mapofzones.calculations.ibctransfers;
//
//import com.mapofzones.calcutalionservice.ibctransfers.repository.mongo.IbcTransferChartRepository;
//import com.mapofzones.calcutalionservice.ibctransfers.repository.mongo.domain.IbcTransferChart;
//import com.mapofzones.calcutalionservice.ibctransfers.services.IbcTransferService;
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
//public class IbcTransferController {
//
//    @Autowired
//    private IbcTransferService ibcTransferService;
//
//    @Autowired
//    private IbcTransferChartRepository chartRepository;
//
//    @GetMapping("/api/ibcTransfersCount/doCalculation")
//    public void doCalculation() {
//        ibcTransferService.doCalculation();
//    }
//
//
//    @GetMapping("/api/ibcTransfersCount/{zone}/{period}")
//    public ResponseEntity<?> findByZone(@PathVariable String zone, @PathVariable String period) {
//
//        Long fromTime = LocalDateTime.now().minus(DurationStyle.SIMPLE.parse(period)).toEpochSecond(UTC);
//        IbcTransferChart chart = chartRepository.findByData_Zone(zone);
//        return new ResponseEntity<>(chart.withPeriod(fromTime), HttpStatus.OK);
//    }
//
//}
