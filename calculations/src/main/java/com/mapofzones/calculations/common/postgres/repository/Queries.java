package com.mapofzones.calculations.common.postgres.repository;

public interface Queries {

    String GET_DELEGATORS_COUNT = """
            with series as (
              select generate_series(
                date_trunc('day', cast(?1 as timestamp)),
                date_trunc('day', cast(?2 as timestamp)),
                cast('1 day' as interval)
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
            , delegators_stats as (
              SELECT
                zone,
                date_trunc('day', datetime) as datetime,
                MAX(delegators_count) as delegators_count
              FROM
                public.zone_parameters
              WHERE
                datetime >= date_trunc('day', cast(?1 as timestamp)) -- - make_interval(hours => 48)
                and date_trunc('day', datetime) <= date_trunc('day', cast(?2 as timestamp))
              GROUP BY
                zone,
                date_trunc('day', datetime)
              ORDER BY
                date_trunc('day', datetime) ASC
            )
                        
            SELECT
              stats_template.zone,
              stats_template.hour as datetime,
              delegators_stats.delegators_count
            FROM
              stats_template
            LEFT JOIN delegators_stats
              ON stats_template.zone = delegators_stats.zone
              and stats_template.hour = delegators_stats.datetime
            """;

}
