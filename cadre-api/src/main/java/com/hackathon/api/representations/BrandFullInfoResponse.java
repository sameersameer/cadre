package com.hackathon.api.representations;

import com.cadre.entities.Campaign;
import com.cadre.entities.Driver;
import com.cadre.entities.GenericStats;
import com.cadre.entities.Stats;
import com.google.api.services.drive.Drive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by juyal.shashank on 16/09/15.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandFullInfoResponse {
    List<CampaignInfo> campaigns;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CampaignInfo{
        Campaign campaign;
        GenericStats campaignStats;
        List<DriverInfo> drivers;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class DriverInfo{
            Driver driver;
            GenericStats driverStats;
        }

    }

}
