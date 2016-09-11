package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Campaign {
    private Integer id;
    private String advertiserId;
    private String campaignName;
    private String advertiserName;
    private String campaignDesc;
    private DateTime startDate;
    private DateTime endDate;
    private Integer distanceCap;
    private Integer NumOfDrivers;
    private CampaignStatus status;
    private Double totalBudget;

    public static class CampaignInfoMapper implements ResultSetMapper<Campaign> {
        @Override
        public Campaign map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            DateTime startDate = new DateTime(resultSet.getTimestamp("start_date"));
            DateTime endDate = new DateTime(resultSet.getTimestamp("end_date"));
            return new Campaign(
                    resultSet.getInt("id"),
                    resultSet.getString("advertiser_id"),
                    resultSet.getString("campaign_name"),
                    resultSet.getString("advertiser_name"),
                    resultSet.getString("campaign_desc"),
                    startDate, endDate,
                    resultSet.getInt("distance_cap"),
                    resultSet.getInt("num_of_drivers"),
                    CampaignStatus.getCampaignStatus(resultSet.getString("status")),
                    resultSet.getDouble("total_budget")
            );
        }
    }

}
