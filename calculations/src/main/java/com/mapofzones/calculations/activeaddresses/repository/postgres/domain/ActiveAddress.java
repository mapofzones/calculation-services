package com.mapofzones.calculations.activeaddresses.repository.postgres.domain;

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
@Table(name = "ACTIVE_ADDRESSES")
public class ActiveAddress extends PostgresEntity {

    @EmbeddedId
    private ActiveAddressId activeAddressId;
    @Column(name = "is_internal_tx")
    private Boolean isInternalTx;
    @Column(name = "is_internal_transfer")
    private Boolean isInternalTransfer;
    @Column(name = "is_external_transfer")
    private Boolean isExternalTransfer;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class ActiveAddressId implements Serializable {

        @Column(name = "ADDRESS")
        private String address;
        @Column(name = "ZONE")
        private String zone;
        @Column(name = "HOUR")
        private LocalDateTime datetime;
        @Column(name = "PERIOD")
        private Integer period;

    }

}
