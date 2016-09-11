package com.hackathon.api;

import com.cadre.dao.*;
import com.hackathon.api.resources.CadreAPIResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.JerseyClient;
import org.skife.jdbi.v2.DBI;

public class CadreAPIApplication extends Application<CadreAPIConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CadreAPIApplication().run(args);
    }

    @Override
    public String getName() {
        return "CadreAPI";
    }

    @Override
    public void initialize(final Bootstrap<CadreAPIConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final CadreAPIConfiguration configuration,
                    final Environment environment) throws Exception {
        final JerseyClient client = (JerseyClient) new JerseyClientBuilder(environment)
                .using(configuration.getClientConfiguration())
                .build("jerseyClient");
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabaseConfiguration(), "mysql");
        final CampaignDAO campaignDao = jdbi.onDemand(CampaignDAO.class);
        final CreativeDAO creativeDao = jdbi.onDemand(CreativeDAO.class);
        final SeverityDAO severityDAO = jdbi.onDemand(SeverityDAO.class);
        final StatsDAO statsDAO = jdbi.onDemand(StatsDAO.class);
        final DriverDAO driverDao = jdbi.onDemand(DriverDAO.class);

        environment.jersey().register(new CadreAPIResource(client, campaignDao, creativeDao, severityDAO, statsDAO, driverDao));
    }

}
