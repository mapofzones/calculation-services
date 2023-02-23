package com.mapofzones.calculations.transactions.repository.postgres;

public interface TransactionsQueries {

    String GET_TRANSACTIONS_COUNT = """
            with series as (
              select generate_series(
                date_trunc('day', cast(?1 AS timestamp)),
                date_trunc('day', cast(?2 AS timestamp)),
                cast('1 day' AS interval)
              ) as hour
            )
                        
            , original_stats as (
              SELECT
                zone,
                date_trunc('day', hour) as hour,
                period,
                sum(txs_cnt) as txs_cnt
              FROM
                public.TOTAL_TX_HOURLY_STATS txs
              WHERE txs.hour >= date_trunc('day', cast(?1 AS timestamp)) --?1
              GROUP BY
                zone,
                date_trunc('day', hour),
                period
              ORDER BY
                date_trunc('day', hour) -- asc or desc sort???
            )
            , stats_template as (
              select
                chain_id as zone,
                hour,
                1 as period
              from
                public.zones
              cross join series
              WHERE
                is_enabled = true
            )
                        
            SELECT
              stats_template.zone,
              stats_template.hour,
              stats_template.period,
              coalesce(txs_cnt, 0) as txs_cnt
            FROM
              stats_template
            LEFT JOIN original_stats
              ON stats_template.zone = original_stats.zone
              and stats_template.hour = original_stats.hour
              and stats_template.period = original_stats.period;
            """;

}
