package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Driver {
    Integer id;
    String deviceId;
    Integer userId;
    String driverName;
    Integer age;
    Integer experience;
    String contactNumber;
    String contactMail;
    Integer vehicleId;
    DriverStatus driverStatus;

    public static class DriverInfoMapper implements ResultSetMapper<Driver> {
        @Override
        public Driver map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Driver(
                    resultSet.getInt("id"),
                    resultSet.getString("device_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("driver_name"),
                    resultSet.getInt("age"),
                    resultSet.getInt("experience"),
                    resultSet.getString("contact_number"),
                    resultSet.getString("contact_mail"),
                    resultSet.getInt("vehicle_id"),
                    DriverStatus.getDriverStatus(resultSet.getString("driver_status"))
            );
        }
    }

}
