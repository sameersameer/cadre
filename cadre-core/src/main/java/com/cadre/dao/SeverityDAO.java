package com.cadre.dao;

import com.cadre.entities.TrafficSeverity;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by purushotham.m on 10/09/16.
 */
@RegisterMapper(TrafficSeverity.MapEntryMapper.class)
public interface SeverityDAO {
    static final String tableName = "severity";

    @SqlQuery("select device_id, sum(distance) from "+tableName+" where time1 > UNIX_TIMESTAMP(CURDATE()) group by 1;")
    List<Map.Entry<String,BigDecimal>> getDailyDistance();

    @SqlQuery("select device_id, sum(distance) from "+tableName+" where time1 > UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL -(DAYOFWEEK(CURDATE()))+1 DAY)) group by 1;")
    List<Map.Entry<String,BigDecimal>> getWeeklyDistance();

    @SqlQuery("select device_id, sum(distance) from "+tableName+" group by 1;")
    List<Map.Entry<String,BigDecimal>> getTotalDistance();

    @SqlQuery("select device_id, sum(severity)*:price from "+tableName+" where time1 > UNIX_TIMESTAMP(CURDATE()) group by 1;")
    List<Map.Entry<String,BigDecimal>> getDailyEarnings(@Bind("price")double price);

    @SqlQuery("select device_id, sum(severity)*:price from "+tableName+" where time1 > UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL -(DAYOFWEEK(CURDATE()))+1 DAY)) group by 1;")
    List<Map.Entry<String,BigDecimal>> getWeeklyEarning(@Bind("price")double price);

    @SqlQuery("select device_id, sum(severity)*:price from "+tableName+" group by 1;")
    List<Map.Entry<String,BigDecimal>> getTotalEarning(@Bind("price")double price);

    @SqlQuery("select UNIX_TIMESTAMP(max(time1)) from "+tableName)
    Long getLastUpdated();
}
