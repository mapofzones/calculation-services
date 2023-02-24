package com.mapofzones.calculations.activeaddresses.repository.postgres;

public interface ActiveAddressesQueries {
    String GET_ACTIVE_ADDRESSES_BY_PERIOD = """
            with series as (
              select generate_series(
                date_trunc('day', cast(?1 as timestamp)),
                date_trunc('day', cast(?2 as timestamp)),
                cast('1 day' AS interval)
              ) as hour
            )
            , stats_template as (
              select
                chain_id as zone,
                hour
              from
                public.zones
              cross join series
              WHERE
                is_enabled = true
            )
            , addresses_stats as (
              SELECT
                zone,
                date_trunc('day', hour) as hour,
                COUNT(*) as active_addresses
              FROM
                public.active_addresses
              WHERE
                is_internal_tx = true
                and hour >= date_trunc('day', cast(?1 as timestamp))
                and hour <= date_trunc('day', cast(?2 as timestamp))
              GROUP BY
                zone,
                date_trunc('day', hour)
            )
            , ibc_addresses_stats as (
              SELECT
                zone,
                date_trunc('day', hour) as hour,
                COUNT(*) as ibc_active_addresses
              FROM
                public.active_addresses
              WHERE
                is_internal_transfer = true
                and hour >= date_trunc('day', cast(?1 as timestamp))
                and hour <= date_trunc('day', cast(?2 as timestamp))
              GROUP BY
                zone,
                date_trunc('day', hour)
            )
                        
            SELECT
              stats.zone,
              stats.hour,
              coalesce(addresses.active_addresses, 0) as active_addresses,
              coalesce(ibc_addresses.ibc_active_addresses, 0) as ibc_active_addresses
            FROM
              stats_template as stats
            LEFT JOIN addresses_stats as addresses
              ON stats.zone = addresses.zone and stats.hour = addresses.hour
            LEFT JOIN ibc_addresses_stats as ibc_addresses
              ON stats.zone = ibc_addresses.zone and stats.hour = ibc_addresses.hour
            """;
}
