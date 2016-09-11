package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by purushotham.m on 10/09/16.
 */
@AllArgsConstructor
@Getter
public class TrafficSeverity {
    Integer severity;

    public static class MapEntryMapper implements ResultSetMapper<Map.Entry<String, BigDecimal>> {
        @Override
        public Map.Entry<String, BigDecimal> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new AbstractMap.SimpleEntry<>(r.getString(1), r.getBigDecimal(2));
        }
    }
}
