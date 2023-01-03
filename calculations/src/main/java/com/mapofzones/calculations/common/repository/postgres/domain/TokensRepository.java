package com.mapofzones.calculations.common.repository.postgres.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository(value = "ibaVolumeTokensRepository")
public interface TokensRepository extends JpaRepository<Token, Token.TokenId> {

    @Query(value = """
            SELECT * FROM tokens t WHERE t.zone = ?1 AND t.base_denom = \
            (SELECT z.base_token_denom FROM zones z WHERE z.chain_id = ?1);
            """, nativeQuery = true)
    Token findMainTokenByZone(String zone);

}
