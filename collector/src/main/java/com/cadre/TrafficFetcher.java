package com.cadre;

import com.cadre.entities.LocationInfo;
import com.cadre.entities.StagnantInfo;
import com.cadre.entities.TrafficSeverity;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sameer.sanagala on 11/09/16.
 */
@Slf4j
public class TrafficFetcher {

  private static String API_KEY = "AIzaSyCTARm9hsmEso_eNtzL2-KieSIELY6xJ-c";

  public static Map.Entry<TrafficSeverity,Double> getTrafficSeverity(LocationInfo initialLocation, LocationInfo finalLocation, Integer timeActual){

    TrafficSeverity trafficSeverity = null;

    WebTarget webTargetDistanceMatrix = getTarget("https://maps.googleapis.com/maps/api/distancematrix/json")
        .queryParam("mode", "driving")
        .queryParam("key",API_KEY)
        .queryParam("origins",initialLocation.getLatitude()+","+initialLocation.getLongitude())
        .queryParam("destinations",finalLocation.getLatitude()+","+finalLocation.getLongitude());

    Response responseDM = null;
    try {
      responseDM = webTargetDistanceMatrix.request(MediaType.APPLICATION_JSON_TYPE)
          .header("Content-type", MediaType.APPLICATION_JSON)
          .get();
    } catch (Exception e){
      log.error("Failed to fetch directions between " + initialLocation.getLatitude() + ","
                + initialLocation.getLongitude() + " and " + finalLocation.getLatitude() + ","
                + finalLocation.getLongitude());
    }

    String jsonResponseDM = responseDM.readEntity(String.class);
    JSONObject jsonObjectDM = new JSONObject(jsonResponseDM);
    Double distance = 0d;
    Integer timeExpected = 0;
    if(jsonObjectDM.getString("status").equals("OK")) {
      JSONArray jsonObjectRows = jsonObjectDM.getJSONArray("rows");
      JSONObject jsonObjectRow = jsonObjectRows.getJSONObject(0);
      JSONArray jsonObjectElements = jsonObjectRow.getJSONArray("elements");
      JSONObject jsonObjectElement = (JSONObject) jsonObjectElements.get(0);
      JSONObject jsonObjectDurationExpected = jsonObjectElement.getJSONObject("duration");
      JSONObject jsonObjectDistance = jsonObjectElement.getJSONObject("distance");
      timeExpected = jsonObjectDurationExpected.getInt("value");
      distance = jsonObjectDistance.getDouble("value");
    } else {
      log.error("Failed to fetch directions between " + initialLocation.getLatitude()+","+initialLocation.getLongitude() + " and " + finalLocation.getLatitude()+","+finalLocation.getLongitude());
    }


    //TODO Check Duration in traffic for calculating stagnant severity
    StagnantInfo stagnantInfo = LocationFetcher.getStagnantInfo(finalLocation.getDeviceId());
    Long stagnantTime = 0L;
    if(distance==0){
      if(stagnantInfo == null){
        stagnantTime = timeActual.longValue();
        StagnantInfo newStagnantInfo = new StagnantInfo();
        newStagnantInfo.setStagnantSince(initialLocation.getTime());
        newStagnantInfo.setDeviceId(initialLocation.getDeviceId());
        LocationFetcher.insertStagnant(newStagnantInfo);
      } else {
        if (stagnantInfo.getStagnantSince() == 0){
          stagnantTime = timeActual.longValue();
          stagnantInfo.setStagnantSince(initialLocation.getTime());
          LocationFetcher.updateStagnantSince(stagnantInfo);
        } else {
          stagnantTime = finalLocation.getTime() - stagnantInfo.getStagnantSince();
        }
      }
    } else {
      if(stagnantInfo != null && stagnantInfo.getStagnantSince() != 0){
        stagnantInfo.setStagnantSince(0L);
        LocationFetcher.updateStagnantSince(stagnantInfo);
      }
    }

    if(stagnantTime != 0L){
      if(stagnantTime < 600000L){
        trafficSeverity = new TrafficSeverity(5);
      } else if (stagnantTime < 1200000L){
        trafficSeverity = new TrafficSeverity(4);
      } else if (stagnantTime < 1800000L){
        trafficSeverity = new TrafficSeverity(3);
      } else if (stagnantTime < 2400000L){
        trafficSeverity = new TrafficSeverity(2);
      } else {
        trafficSeverity = new TrafficSeverity(1);
      }
      return new AbstractMap.SimpleEntry(trafficSeverity, distance);
    }

    WebTarget webTargetDirections = getTarget("https://maps.googleapis.com/maps/api/directions/json")
        .queryParam("origin", initialLocation.getLatitude() + "," + initialLocation.getLongitude())
        .queryParam("destination",finalLocation.getLatitude()+","+finalLocation.getLongitude())
        .queryParam("mode","driving")
        .queryParam("departure_time","now")
        .queryParam("key",API_KEY);
    Response response = null;
    try {
      response = webTargetDirections.request(MediaType.APPLICATION_JSON_TYPE)
          .header("Content-type", MediaType.APPLICATION_JSON)
          .get();
    } catch (Exception e){
      log.error("Failed to fetch directions between " + initialLocation.getLatitude() + ","
                + initialLocation.getLongitude() + " and " + finalLocation.getLatitude() + ","
                + finalLocation.getLongitude());
    }

    String jsonResponseDirections = response.readEntity(String.class);
    JSONObject jsonObjectDirections = new JSONObject(jsonResponseDirections);
    Integer timeInTraffic = 0;
    if(jsonObjectDirections.getString("status").equals("OK")) {
      JSONArray jsonObjectRoutes = jsonObjectDirections.getJSONArray("routes");
      JSONObject jsonObjectBestRoute = jsonObjectRoutes.getJSONObject(0);
      JSONArray jsonObjectLegs = jsonObjectBestRoute.getJSONArray("legs");
      for(int i=0; i<jsonObjectLegs.length(); i++) {
        JSONObject jsonObjectLeg = (JSONObject) jsonObjectLegs.get(0);
        JSONObject jsonObjectDurationInTraffic = jsonObjectLeg.getJSONObject("duration_in_traffic");
        timeInTraffic = timeInTraffic + jsonObjectDurationInTraffic.getInt("value");
      }
    } else {
      log.error("Failed to fetch directions between " + initialLocation.getLatitude()+","+initialLocation.getLongitude() + " and " + finalLocation.getLatitude()+","+finalLocation.getLongitude());
    }

    //TODO Should consider some buffer time
    if(timeInTraffic < timeActual){
      timeActual = timeInTraffic;
    }

    Double trafficIndex = timeActual*1.0/timeExpected*1.0;

    //Model it better and use timeInTraffic also to calculate trafficSeverity
    if(trafficIndex > 0 && trafficIndex < 0.5){
      trafficSeverity = new TrafficSeverity(1);
    } else if (trafficIndex > 0.5 && trafficIndex < 1){
      trafficSeverity = new TrafficSeverity(2);
    } else if (trafficIndex > 1 && trafficIndex < 2){
      trafficSeverity = new TrafficSeverity(3);
    } else if (trafficIndex > 2 && trafficIndex < 3){
      trafficSeverity = new TrafficSeverity(4);
    } else if (trafficIndex > 3){
      trafficSeverity = new TrafficSeverity(5);
    }

    return new AbstractMap.SimpleEntry(trafficSeverity, distance);
  }

  public static WebTarget getTarget(String path){
    final Configuration clientConfig = new ClientConfig(JacksonJaxbJsonProvider.class);
    Client client = ClientBuilder.newClient(clientConfig);
    client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
    WebTarget webTarget = client.target(path);
    return webTarget;
  }
}
