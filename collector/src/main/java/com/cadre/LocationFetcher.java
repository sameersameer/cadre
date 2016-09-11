package com.cadre;

import com.cadre.dao.LocationDAO;
import com.cadre.dao.SeverityDAO;
import com.cadre.entities.LocationInfo;
import com.cadre.entities.StagnantInfo;
import com.cadre.entities.TrafficSeverity;

import org.skife.jdbi.v2.DBI;

import java.util.List;

/**
 * Created by sameer.sanagala on 11/09/16.
 */
public class LocationFetcher {

  private static LocationDAO locationDAO;
  private static SeverityDAO severityDAO;

  static {
    DBI dbi = createDBI();
    locationDAO = dbi.onDemand(LocationDAO.class);
    severityDAO = dbi.onDemand(SeverityDAO.class);
  }

  public static DBI createDBI(){

    System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
    }
    String url = "jdbc:mysql://127.0.0.1:3306/cadredb";
    String user = "root";
    String password = "";
    DBI jdbi = new DBI(url, user, password);
    return jdbi;

  }

  public static List<LocationInfo> getLocations(){
    return locationDAO.getUnprocessedLocations();
  }

  public static LocationInfo getPastLocation(String deviceId, Long timestamp){
    return locationDAO.getPastLocation(deviceId, timestamp);
  }

  public static void insertTrafficSeverity(LocationInfo initialLocation, LocationInfo finalLocation, TrafficSeverity trafficSeverity, Double distance){
    severityDAO.insert(initialLocation, finalLocation, trafficSeverity.getSeverity(), distance);
  }

  public static void markProcessed(LocationInfo locationInfo){
    locationDAO.markProcessed(locationInfo);
  }

  public static StagnantInfo getStagnantInfo(String deviceId){
    return locationDAO.getStagnantInfo(deviceId);
  }

  public static void updateStagnantSince(StagnantInfo stagnantInfo){
    locationDAO.setStagnantSince(stagnantInfo);
  }

  public static void insertStagnant(StagnantInfo stagnantInfo){
    locationDAO.insertStagnant(stagnantInfo);
  }

}
