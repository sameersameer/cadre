package com.cadre.dao;

import com.cadre.entities.Creative;
import com.cadre.entities.Stats;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by purushotham.m on 10/09/16.
 */
@RegisterMapper(Stats.StatsMapper.class)
public interface StatsDAO {
    static final String tableName = "stats";
    @SqlQuery("SELECT * FROM " + tableName + " where device_idd = :id")
    Stats getDeviceStats(@Bind("id") String deviceId);

    @SqlBatch("INSERT INTO `"+tableName+"` (`device_id`, `mileage_d`, `mileage_w`, `mileage_t`, `earnings_d`, `earnings_w`, `earnings_t`, `last_udpated_at`)\n" +
            "VALUES\n" +
            "(:deviceStat.deviceId, :deviceStat.dayMileage, :deviceStat.weekMileage, :deviceStat.totalMileage, :deviceStat.dayEarning, :deviceStat.weekEarning, :deviceStat.totalEarning, :deviceStat.lastUpdatedAt)\n" +
            "ON DUPLICATE KEY UPDATE \n" +
            "  mileage_d = VALUES(mileage_d),\n" +
            "  mileage_w = VALUES(mileage_w),\n" +
            "  mileage_t = VALUES(mileage_t),\n" +
            "  earnings_d = VALUES(earning_d),\n" +
            "  earnings_w = VALUES(earning_w),\n" +
            "  earnings_t = VALUES(earning_t),\n" +
            "  last_updated_at = VALUES(last_updated_at);")
    int insertOrUpdate(@BindBean("deviceStat") Iterable<Stats> deviceStat);
}
