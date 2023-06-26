package com.mapofzones.calculations.controllers;

import com.mapofzones.calculations.services.IChartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {

    private final IChartService chartService;

    public ChartController(IChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/api/beta/delegationsAmountChart/{zone}/{period}")
    public ResponseEntity<?> findDelegationsAmountChart(@PathVariable String zone, @PathVariable String period) {
        return new ResponseEntity<>(chartService.findDelegationAmountChart(zone, period), HttpStatus.OK);
    }

    @GetMapping("/api/beta/delegatorCountChart/{zone}/{period}")
    public ResponseEntity<?> findDelegatorsCountChart(@PathVariable String zone, @PathVariable String period) {
        if (zone.contains(","))
            return new ResponseEntity<>(chartService.findBulkDelegatorsCountChart(List.of(zone.split(",")), period), HttpStatus.OK);
        else
            return new ResponseEntity<>(chartService.findDelegatorsCountChart(zone, period), HttpStatus.OK);
    }

    @GetMapping("/api/beta/ibcTransferChart/{zone}/{period}")
    public ResponseEntity<?> findIbcTransferChart(@PathVariable String zone, @PathVariable String period) {
        return new ResponseEntity<>(chartService.findIbcTransferChart(zone, period), HttpStatus.OK);
    }

    @GetMapping("/api/beta/ibcVolumeChart/{zone}/{period}")
    public ResponseEntity<?> findIbcVolumeChart(@PathVariable String zone, @PathVariable String period) {
        return new ResponseEntity<>(chartService.findIbcVolumeChart(zone, period), HttpStatus.OK);
    }

    @GetMapping("/api/beta/txsChart/{zone}/{period}")
    public ResponseEntity<?> findTxsChart(@PathVariable String zone, @PathVariable String period) {
        if (zone.contains(","))
            return new ResponseEntity<>(chartService.findBulkTxsChart(List.of(zone.split(",")), period), HttpStatus.OK);
        else
            return new ResponseEntity<>(chartService.findTxsChart(zone, period), HttpStatus.OK);
    }

    @GetMapping("/api/beta/activeAddressesCountChart/{zone}/{period}")
    public ResponseEntity<?> findActiveAddressesCountChart(@PathVariable String zone, @PathVariable String period) {
        if (zone.contains(","))
            return new ResponseEntity<>(chartService.findBulkActiveAddressesCountChart(List.of(zone.split(",")), period), HttpStatus.OK);
        else
            return new ResponseEntity<>(chartService.findActiveAddressesCountChart(zone, period), HttpStatus.OK);
    }

    @GetMapping("/api/beta/activeAddressesCountStats/{zone}")
    public ResponseEntity<?> findActiveAddressesCountStats(@PathVariable String zone) {
        return new ResponseEntity<>(chartService.findActiveAddressesCountStats(zone), HttpStatus.OK);
    }

    @GetMapping("/api/beta/interchainActiveAddressesCountStats")
    public ResponseEntity<?> findInterchainActiveAddressesCountStats() {
        return new ResponseEntity<>(chartService.findInterchainActiveAddressesCountStats(), HttpStatus.OK);
    }
}
