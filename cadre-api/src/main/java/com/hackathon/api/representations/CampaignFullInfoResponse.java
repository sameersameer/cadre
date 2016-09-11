package com.hackathon.api.representations;

import com.cadre.entities.Campaign;
import com.cadre.entities.Creative;
import com.cadre.entities.Driver;
import com.cadre.entities.GenericStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by juyal.shashank on 16/09/15.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFullInfoResponse {
    List<CampaignInfo> campaigns;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CampaignInfo{
        Campaign campaign;
        Map<String,List<Creative>> type;

    }

}
