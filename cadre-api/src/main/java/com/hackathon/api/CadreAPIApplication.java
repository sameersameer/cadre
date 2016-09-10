package com.hackathon.api;

import com.hackathon.api.db.GPSInfoDao;
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
        return "TrackerProxy";
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
        final GPSInfoDao gpsInfoDao = jdbi.onDemand(GPSInfoDao.class);

        environment.jersey().register(new CadreAPIResource(client, gpsInfoDao));
    }

}
