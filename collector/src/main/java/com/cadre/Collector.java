package com.cadre;

import com.cadre.entities.LocationInfo;
import com.cadre.entities.TrafficSeverity;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sameer.sanagala on 10/09/16.
 */
@Slf4j
public class Collector {

  private final Long FETCH_TIME_INTERVAL_IN_MS = 10000L;

  public void run(){
    List<LocationInfo> locations;
    while(true){
      locations = LocationFetcher.getLocations();
      for(LocationInfo locationInfo: locations){
        //TODO check if device id is active in a campaign
        LocationInfo pastLocation = LocationFetcher.getPastLocation(locationInfo.getDeviceId(),locationInfo.getTime() - FETCH_TIME_INTERVAL_IN_MS);
        if(pastLocation == null){
          LocationFetcher.markProcessed(locationInfo);
          continue;
        }

        Map.Entry<TrafficSeverity, Double> severityAndDistanceEntry = TrafficFetcher.getTrafficSeverity(pastLocation, locationInfo,
                                                                                                        (int) (FETCH_TIME_INTERVAL_IN_MS/1000));
        try {
          LocationFetcher
              .insertTrafficSeverity(pastLocation, locationInfo, severityAndDistanceEntry.getKey(),
                                     severityAndDistanceEntry.getValue());
        } catch (Exception e){
          log.error("Failed to insert severity to db" + e);
        }

        LocationFetcher.markProcessed(locationInfo);

      }
      System.out.println(locations);
    }
  }

  public void shutdown(){

  }
}
