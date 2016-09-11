package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Creative {
    Integer id;
    Integer campaignId;
    String creativeUrl;
    DecalType decalType;
    Integer isPrimary;

    public static class CreativeInfoMapper implements ResultSetMapper<Creative> {
        @Override
        public Creative map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Creative(
                    resultSet.getInt("id"),
                    resultSet.getInt("campaign_id"),
                    resultSet.getString("creative_url"),
                    DecalType.getDecalType(resultSet.getString("decal_type")),
                    resultSet.getInt("is_primary")
            );
        }
    }

}
