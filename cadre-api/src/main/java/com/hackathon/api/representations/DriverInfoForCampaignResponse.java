package com.hackathon.api.representations;

import com.cadre.entities.Campaign;
import com.cadre.entities.Driver;
import com.cadre.entities.GenericStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by juyal.shashank on 16/09/15.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfoForCampaignResponse {
    Campaign campaign;
    Driver driver;
    GenericStats driverStats;

}
