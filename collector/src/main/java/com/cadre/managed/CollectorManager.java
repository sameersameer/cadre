package com.cadre.managed;

import com.cadre.Collector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sameer.sanagala on 10/09/16.
 */

@Slf4j
public class CollectorManager implements Managed {

  private Collector collector;
  private static final ExecutorService manager = Executors.newSingleThreadExecutor(new ThreadFactory() {
    public Thread newThread(Runnable r) {
      Thread thread = new Thread(r, "Collector");
      return thread;
    }
  });

  @Override
  public void start() throws Exception {
    manager.submit(() -> {
      try {
      collector = new Collector();
      collector.run();
    } catch (Exception e){
      log.error("Exception starting Orchestrator",e);
      throw new RuntimeException(e);
    }
  });
  }

  @Override
  public void stop() throws Exception {
    collector.shutdown();
    manager.shutdown();
  }
}
