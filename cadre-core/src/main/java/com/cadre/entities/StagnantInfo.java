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
 * Created by sameer.sanagala on 11/09/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StagnantInfo {
  private Long stagnantSince;
  private String deviceId;

  public static class StagnantInfoMapper implements ResultSetMapper<StagnantInfo> {

    @Override
    public StagnantInfo map(int index, ResultSet r, StatementContext ctx) throws SQLException {
      return new StagnantInfo(r.getLong("stagnant_since"), r.getString("device_id"));
    }
  }
}
