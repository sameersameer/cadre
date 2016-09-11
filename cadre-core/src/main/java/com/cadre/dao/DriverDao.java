package com.cadre.dao;

import com.cadre.entities.Creative;
import com.cadre.entities.Driver;
import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(Driver.DriverInfoMapper.class)
public interface DriverDAO {

    static final String tableName = "driver";
    static final String deviceMapTableName = "device_campaign_mapping";
    static final String campaignTable = "campaign";


    @Timed
    @SqlQuery("SELECT * FROM " + tableName)
    List<Driver> getAllDrivers();

    @SqlQuery("SELECT * FROM " + tableName + " where id = :id")
    Driver getDriver(@Bind("id") Integer id);

    @SqlQuery("SELECT dr.* FROM " + tableName + " dr," + deviceMapTableName + " dc where campaign_id = :id and dc.device_id = dr.device_id")
    List<Driver> getDriversForCampaign(@Bind("id") Integer id);

    @SqlQuery("SELECT dc.campaign_id FROM " + tableName + " dr," + deviceMapTableName + " dc," + campaignTable + " ct where dr.id = :driverId and dc.campaign_id=ct.id and ct.status IN ('READY','RUNNING','DRAFT') and dc.device_id = dr.device_id")
    Integer getActiveCampaignIdForDriver(@Bind("driverId") Integer driverId);



}
