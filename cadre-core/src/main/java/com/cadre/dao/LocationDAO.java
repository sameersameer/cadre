package com.cadre.dao;

import com.cadre.entities.LocationInfo;
import com.cadre.entities.StagnantInfo;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by sameer.sanagala on 10/09/16.
 */
@LogSqlFactory
public interface LocationDAO {

  String locationInfoTable = "location_info";
  String stagnantInfoTable = "stagnant";

  @RegisterMapper(LocationInfo.LocationInfoMapper.class)
  @SqlQuery("SELECT * FROM " + locationInfoTable + " WHERE device_id = :deviceId")
  List<LocationInfo> get(@Bind("deviceId") String deviceId);

  @RegisterMapper(StagnantInfo.StagnantInfoMapper.class)
  @SqlQuery("SELECT * FROM " + stagnantInfoTable + " WHERE device_id = :deviceId")
  StagnantInfo getStagnantInfo(@Bind("deviceId") String deviceId);

  @RegisterMapper(LocationInfo.LocationInfoMapper.class)
  @SqlQuery("SELECT * FROM " + locationInfoTable + " WHERE processed = 0")
  List<LocationInfo> getUnprocessedLocations();

  //TODO get nearest timestamp
  @RegisterMapper(LocationInfo.LocationInfoMapper.class)
  @SqlQuery("SELECT * FROM " + locationInfoTable + " WHERE device_id =:deviceId and timestamp = :timestamp")
  LocationInfo getPastLocation(@Bind("deviceId") String deviceId, @Bind("timestamp") Long timestamp);

  @SqlUpdate("UPDATE " + locationInfoTable + " set processed = 1 where device_id=::locationInfo.deviceId and timestamp=:locationInfo.time")
  void markProcessed(@BindBean("locationInfo") LocationInfo locationInfo);

  @SqlUpdate("UPDATE " + stagnantInfoTable + " set stagnant_since = :stagnantInfo.stagnantSince where device_id=:stagnantInfo.deviceId")
  void setStagnantSince(@BindBean("stagnantInfo") StagnantInfo stagnantInfo);

  @SqlUpdate("INSERT INTO " + locationInfoTable +  " (latitude,longitude,timestamp,device_id) " +
             "VALUES (:locationInfo.latitude, :locationInfo.longitude, :locationInfo.time, :locationInfo.deviceId)")
  Integer insertInfo(@BindBean("locationInfo") LocationInfo locationInfo);

  @SqlUpdate("INSERT INTO " + stagnantInfoTable +  " (device_id,stagnant_since) " +
             "VALUES (:stagnantInfo.deviceId, :stagnantInfo.stagnantSince)")
  Integer insertStagnant(@BindBean("stagnantInfo") StagnantInfo stagnantInfo);

}
