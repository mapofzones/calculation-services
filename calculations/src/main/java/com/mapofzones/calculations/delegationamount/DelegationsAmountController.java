//package com.mapofzones.calculations.delegationamount;
//
//import com.mapofzones.calcutalionservice.delegationamount.repository.mongo.MongoChartRepository;
//import com.mapofzones.calcutalionservice.delegationamount.repository.mongo.domain.DelegationsAmountChart;
//import com.mapofzones.calcutalionservice.delegationamount.service.DelegationsAmountService;
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
//public class DelegationsAmountController {
//
//    @Autowired
//    private DelegationsAmountService delegationsAmountService;
//
//    @Autowired
//    private MongoChartRepository mongoRepository;
//
//    @GetMapping("/api/delegationsAmountChart/doCalculation/{zone}")
//    public void doCalculation(@PathVariable String zone) {
//        delegationsAmountService.doCalculation(zone);
//    }
//
//    @GetMapping("/api/delegationsAmountChart/doCalculation")
//    public void doCalculation() {
//        delegationsAmountService.doCalculation();
//    }
//
//
//    @GetMapping("/api/delegationsAmountChart/{zone}/{period}")
//    public ResponseEntity<?> findByZone(@PathVariable String zone, @PathVariable String period) {
//
//        Long fromTime = LocalDateTime.now().minus(DurationStyle.SIMPLE.parse(period)).toEpochSecond(UTC);
//        DelegationsAmountChart chart = mongoRepository.findByData_Zone(zone);
//        return new ResponseEntity<>(chart.withPeriod(fromTime), HttpStatus.OK);
//    }
//}
////1671526800
////1671526350