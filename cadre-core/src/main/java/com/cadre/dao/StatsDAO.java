package com.cadre.dao;

import com.cadre.entities.Creative;
import com.cadre.entities.GenericStats;
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

    @RegisterMapper(GenericStats.GenericStatsMapper.class)
    @SqlQuery("select sum(mileage_d) as mileage_d, sum(mileage_w) as mileage_w," +
            "sum(mileage_t) as mileage_t,sum(earnings_d) as earnings_d," +
            "sum(earnings_w) as earnings_w,sum(earnings_t) as earnings_t from " +
            "stats s,device_campaign_mapping d where campaign_id = :campaignId and d.device_id=s.device_id group by campaign_id")
    GenericStats getGenericStatsForCampaigns(@Bind("campaignId") Integer campaignId);

    @RegisterMapper(GenericStats.GenericStatsMapper.class)
    @SqlQuery("select sum(mileage_d) as mileage_d, sum(mileage_w) as mileage_w," +
            "sum(mileage_t) as mileage_t,sum(earnings_d) as earnings_d," +
            "sum(earnings_w) as earnings_w,sum(earnings_t) as earnings_t from " +
            "stats where device_id = :deviceId group by device_id")
    GenericStats getGenericStatsForDevice(@Bind("deviceId") String deviceId);

}
