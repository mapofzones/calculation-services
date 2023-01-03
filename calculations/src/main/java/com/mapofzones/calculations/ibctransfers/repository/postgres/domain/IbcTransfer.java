package com.mapofzones.calculations.ibctransfers.repository.postgres.domain;

import com.mapofzones.calculations.common.domain.PostgresEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "IBC_TRANSFER_HOURLY_STATS")
public class IbcTransfer extends PostgresEntity {

    @EmbeddedId
    private IbcTransferId id;

    @Column(name = "TXS_CNT")
    private Integer ibcTransfersCount;

    @Column(name = "TXS_FAIL_CNT")
    private Integer ibcTransfersFailCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class IbcTransferId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "ZONE_SRC")
        private String zoneSource;

        @Column(name = "ZONE_DEST")
        private String zoneDest;

        @Column(name = "IBC_CHANNEL")
        private String ibcChannel;

        @Column(name = "HOUR")
        private LocalDateTime datetime;

        @Column(name = "PERIOD")
        private Integer period;
    }

}
