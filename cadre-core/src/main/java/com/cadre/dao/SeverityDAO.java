package com.cadre.dao;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by purushotham.m on 10/09/16.
 */
public interface SeverityDAO {
    static final String tableName = "severity";

    @SqlQuery("select device_id, sum(distance) from "+tableName+" where time1 > UNIX_TIMESTAMP(CURDATE()) group by 1;")
    Map<String,BigDecimal> getDailyDistance();

    @SqlQuery("select device_id, sum(distance) from "+tableName+" where time1 > UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL -(DAYOFWEEK(CURRDATE()))+1 DAY)) group by 1;")
    Map<String,BigDecimal> getWeeklyDistance();

    @SqlQuery("select device_id, sum(distance) from "+tableName+" group by 1;")
    Map<String,BigDecimal> getTotalDistance();

    @SqlQuery("select device_id, sum(distance)*severity*:price from "+tableName+" where time1 > UNIX_TIMESTAMP(CURDATE()) group by 1;")
    Map<String,BigDecimal> getDailyEarnings(@Bind("price")double price);

    @SqlQuery("select device_id, sum(distance)*severity*:price from "+tableName+" where time1 > UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL -(DAYOFWEEK(CURRDATE()))+1 DAY)) group by 1;")
    Map<String,BigDecimal> getWeeklyEarning(@Bind("price")double price);

    @SqlQuery("select device_id, sum(distance)*severity*:price from "+tableName+" group by 1;")
    Map<String,BigDecimal> getTotalEarning(@Bind("price")double price);

    @SqlQuery("select UNIX_TIMESTAMP(max(last_updated_at)) from "+tableName)
    Long getLastUpdated();
}
