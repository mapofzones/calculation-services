package com.mapofzones.calculations.common.domain;

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

import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TOKENS")
public class Token {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class TokenId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "BASE_DENOM")
        private String baseDenom;
    }

    @EmbeddedId
    private TokenId tokenId;

    @Column(name = "SYMBOL_POINT_EXPONENT")
    private Integer exponent;
}
