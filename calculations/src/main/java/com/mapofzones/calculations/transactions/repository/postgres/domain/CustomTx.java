package com.mapofzones.calculations.transactions.repository.postgres.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CustomTx {

    private String zone;
    private LocalDateTime datetime;
    private Integer txsCount;

}
