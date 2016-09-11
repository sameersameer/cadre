package com.cadre.dao;

import com.cadre.entities.Campaign;
import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(Campaign.CampaignInfoMapper.class)
public interface CampaignDAO {

    static final String tableName = "campaign";

    @Timed
    @SqlQuery("SELECT * FROM " + tableName)
    List<Campaign> getAllCampaigns();

    @SqlQuery("SELECT * FROM " + tableName + " where id = :id")
    Campaign getCampaign(@Bind("id") Integer id);

    @SqlQuery("SELECT * FROM " + tableName + " where advertiser_id = :advertiserId")
    List<Campaign> getCampaignsForAdvertiser(@Bind("advertiserId") String advertiserId);

}
