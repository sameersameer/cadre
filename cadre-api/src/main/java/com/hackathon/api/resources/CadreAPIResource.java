package com.hackathon.api.resources;

import com.cadre.dao.*;
import com.cadre.entities.Campaign;
import com.cadre.entities.Driver;
import com.cadre.entities.GenericStats;
import com.cadre.entities.Stats;
import com.codahale.metrics.annotation.Timed;
import com.hackathon.api.representations.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by juyal.shashank on 22/07/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class CadreAPIResource {

    private JerseyClient client;
    private CampaignDAO campaignDao;
    private CreativeDAO creativeDao;
    private SeverityDAO severityDAO;
    private StatsDAO statsDAO;
    private DriverDAO driverDao;


    @GET
    @Timed
    @Path("/check")
    public Response basicService() throws Exception {
        BasicAPIResponse basicAPIResponse = new BasicAPIResponse("Success");
        return Response.status(Response.Status.ACCEPTED).entity(basicAPIResponse).build();
    }

    @GET
    @Timed
    @Path("/brands/{advertiserId}")
    public Response getBrandInfo(@PathParam("advertiserId") String advertiserId) throws Exception {
        if(advertiserId == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("AdvertiserId should not be empty")).build();
        }
        List<Campaign> campaigns = campaignDao.getCampaignsForAdvertiser(advertiserId);
        BrandFullInfoResponse brandFullInfoResponse = new BrandFullInfoResponse();
        List<BrandFullInfoResponse.CampaignInfo> campaignInfos = new ArrayList<>();
        for(Campaign campaign: campaigns){
            BrandFullInfoResponse.CampaignInfo campaignInfo = new BrandFullInfoResponse.CampaignInfo();
            List<BrandFullInfoResponse.CampaignInfo.DriverInfo> driverInfoList = new ArrayList<>();

            List<Driver> drivers = driverDao.getDriversForCampaign(campaign.getId());
            for(Driver driver: drivers){
                BrandFullInfoResponse.CampaignInfo.DriverInfo driverInfo = new BrandFullInfoResponse.CampaignInfo.DriverInfo();
                GenericStats driverStats = statsDAO.getGenericStatsForDevice(driver.getDeviceId());
                driverInfo.setDriver(driver);
                driverInfo.setDriverStats(driverStats);
                driverInfoList.add(driverInfo);
            }

            GenericStats campaignStats = statsDAO.getGenericStatsForCampaigns(campaign.getId());
            campaignInfo.setCampaign(campaign);
            campaignInfo.setCampaignStats(campaignStats);
            campaignInfo.setDrivers(driverInfoList);
            campaignInfos.add(campaignInfo);
        }
        brandFullInfoResponse.setCampaigns(campaignInfos);
        return Response.status(Response.Status.OK).entity(brandFullInfoResponse).build();
    }


    @GET
    @Timed
    @Path("/driver/{driverId}")
    public Response getBrandInfo(@PathParam("driverId") Integer driverId) throws Exception {
        if(driverId == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("driverId should not be empty")).build();
        }
        Integer campaignId = driverDao.getActiveCampaignIdForDriver(driverId);
        CheckDriverResponse checkDriverResponse = new CheckDriverResponse(campaignId);
        return Response.status(Response.Status.OK).entity(checkDriverResponse).build();
    }

    @GET
    @Timed
    @Path("/driver/{driverId}/{campaignId}")
    public Response getBrandInfo(@PathParam("driverId") Integer driverId,
                                 @PathParam("campaignId") Integer campaignId) throws Exception {
        Integer verifyCampaignId = driverDao.getActiveCampaignIdForDriver(driverId);
        Campaign campaign = campaignDao.getCampaign(campaignId);
        if(campaign == null || verifyCampaignId == null || (!campaignId.equals(verifyCampaignId))){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("No Such Campaign For this Driver")).build();
        }
        Driver driver = driverDao.getDriver(driverId);
        GenericStats genericStats = statsDAO.getGenericStatsForDevice(driver.getDeviceId());
        DriverInfoForCampaignResponse driverInfoForCampaignResponse = new DriverInfoForCampaignResponse(campaign,driver,genericStats);
        return Response.status(Response.Status.OK).entity(driverInfoForCampaignResponse).build();
    }

    /*@GET
    @Timed
    @Path("/driver/{driverId}/fetch")
    public Response getBrandInfo(@PathParam("driverId") Integer driverId,
                                 @PathParam("campaignId") Integer campaignId) throws Exception {
        Integer verifyCampaignId = driverDao.getActiveCampaignIdForDriver(driverId);
        Campaign campaign = campaignDao.getCampaign(campaignId);
        if(campaign == null || verifyCampaignId == null || (!campaignId.equals(verifyCampaignId))){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("No Such Campaign For this Driver")).build();
        }
        Driver driver = driverDao.getDriver(driverId);
        GenericStats genericStats = statsDAO.getGenericStatsForDevice(driver.getDeviceId());
        DriverInfoForCampaignResponse driverInfoForCampaignResponse = new DriverInfoForCampaignResponse(campaign,driver,genericStats);
        return Response.status(Response.Status.OK).entity(driverInfoForCampaignResponse).build();
    }*/






}
