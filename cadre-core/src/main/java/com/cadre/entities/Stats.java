package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by purushotham.m on 11/09/16.
 */
@AllArgsConstructor
@Data
public class Stats {
    String deviceId;
    BigDecimal dayMileage;
    BigDecimal weekMileage;
    BigDecimal totalMileage;
    BigDecimal dayEarning;
    BigDecimal weekEarning;
    BigDecimal totalEarning;
    DateTime lastUpdatedAt;

    public static class StatsMapper implements ResultSetMapper<Stats> {
        @Override
        public Stats map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            DateTime lastUpdatedAt = new DateTime(resultSet.getTimestamp("last_updated_at"));
            return new Stats(
                resultSet.getString("device_id"),
                resultSet.getBigDecimal("mileage_d"),
                resultSet.getBigDecimal("mileage_w"),
                resultSet.getBigDecimal("mileage_t"),
                resultSet.getBigDecimal("earnings_d"),
                resultSet.getBigDecimal("earnings_w"),
                resultSet.getBigDecimal("earnings_t"),
                lastUpdatedAt
            );
        }
    }


}
