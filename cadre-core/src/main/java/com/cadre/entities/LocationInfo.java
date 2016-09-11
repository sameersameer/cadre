package com.cadre.entities;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by sameer.sanagala on 10/09/16.
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LocationInfo {
    private Double latitude;
    private Double longitude;
    private Long time;
    private String deviceId;

    public static class LocationInfoMapper implements ResultSetMapper<LocationInfo>{

      @Override
      public LocationInfo map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new LocationInfo(r.getDouble("latitude"), r.getDouble("longitude"), r.getLong("timestamp"), r.getString("device_id"));
      }
    }
}
