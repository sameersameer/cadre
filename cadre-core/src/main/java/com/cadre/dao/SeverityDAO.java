package com.cadre.dao;

import com.cadre.entities.LocationInfo;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by sameer.sanagala on 11/09/16.
 */
@LogSqlFactory
public interface SeverityDAO {

  String severityTable = "severity";

  @SqlUpdate("INSERT INTO " + severityTable +  " (lat1,long1,time1,lat2,long2,time2,severity,distance,device_id) " +
             "VALUES (:initialLocation.latitude, :initialLocation.longitude, :initialLocation.time, :finalLocation.latitude, :finalLocation.longitude, :finalLocation.time, :severity, :distance, :initialLocation.deviceId)")
  Integer insert(@BindBean("initialLocation") LocationInfo initialLocation, @BindBean("finalLocation") LocationInfo finalLocation, @Bind("severity") Integer severity, @Bind("distance") Double distance);

}
