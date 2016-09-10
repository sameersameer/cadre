package com.cadre.dao;

import com.cadre.entities.Creative;
import com.cadre.entities.Stats;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by purushotham.m on 10/09/16.
 */
@RegisterMapper(Stats.StatsMapper.class)
public interface StatsDAO {
    static final String tableName = "stats";
    @SqlQuery("SELECT * FROM " + tableName + " where id = :id")
    Stats getDeviceStats(@Bind("id") String deviceId);
}
