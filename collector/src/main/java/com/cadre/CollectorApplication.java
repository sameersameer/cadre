package com.cadre;

import com.cadre.managed.CollectorManager;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sameer.sanagala on 10/09/16.
 */

@Slf4j
public class CollectorApplication extends Application<CollectorConfiguration>{

  public static void main(String[] args) throws Exception {
    new CollectorApplication().run(args);
    log.info("Orchestrator setup is done");
  }

  @Override
  public void run(CollectorConfiguration configuration, Environment environment) throws Exception {
    CollectorManager collectorManager = new CollectorManager();
    environment.lifecycle().manage(collectorManager);
  }

  @Override
  public void initialize(Bootstrap<CollectorConfiguration> bootstrap) {
  }

}
