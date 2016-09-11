package com.cadre;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sameer.sanagala on 10/09/16.
 */
public class CollectorConfiguration extends Configuration {
  @Valid
  @NotNull
  @JsonProperty("database")
  private DataSourceFactory DBFactory;

  @Valid
  @NotNull
  @Getter
  @Setter
  private JerseyClientConfiguration clientConfiguration = new JerseyClientConfiguration();
}
