package com.mapofzones.calculations.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(of = {"zoneParametersId"}, callSuper = false)
public class ParameterEntity extends PostgresEntity {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class ZoneParametersId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "DATETIME")
        private LocalDateTime datetime;
    }

    @EmbeddedId
    ZoneParametersId zoneParametersId;

}
