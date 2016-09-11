package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class GenericStats {
    BigDecimal dayMileage;
    BigDecimal weekMileage;
    BigDecimal totalMileage;
    BigDecimal dayEarning;
    BigDecimal weekEarning;
    BigDecimal totalEarning;

    public static class GenericStatsMapper implements ResultSetMapper<GenericStats> {
        @Override
        public GenericStats map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new GenericStats(
                resultSet.getBigDecimal("mileage_d"),
                resultSet.getBigDecimal("mileage_w"),
                resultSet.getBigDecimal("mileage_t"),
                resultSet.getBigDecimal("earnings_d"),
                resultSet.getBigDecimal("earnings_w"),
                resultSet.getBigDecimal("earnings_t")
            );
        }
    }


}
