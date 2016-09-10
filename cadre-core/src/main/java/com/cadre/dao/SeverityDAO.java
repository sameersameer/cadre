package com.cadre.dao;

import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by purushotham.m on 10/09/16.
 */
public interface SeverityDAO {
    static final String tableName = "severity";

    @SqlQuery("select device_id, sum(distance) from "+tableName+" where time1 > UNIX_TIMESTAMP(CURDATE()) group by 1;")
    Map.Entry<String,BigDecimal> getDailyDistance();

    @SqlQuery("select device_id, sum(distance) from "+tableName+" where time1 > UNIX_TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL -(DAYOFWEEK(CURRDATE()))+1 DAY)) group by 1;")
    Map.Entry<String,BigDecimal> getWeeklyDistance();

    @SqlQuery("select device_id, sum(distance) from "+tableName+" group by 1;")
    Map.Entry<String,BigDecimal> getTotalDistance();
}
