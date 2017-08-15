package com.mainstreethub.contentcalendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainstreethub.contentcalendar.config.ClapiConfiguration;
import com.mainstreethub.environment.Environment;
import com.mainstreethub.mshauth.config.MshAuthBundleConfiguration;
import com.mainstreethub.mshauth.config.MshAuthConfiguration;
import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ContentCalendarConfiguration extends Configuration
    implements MshAuthBundleConfiguration {
  @Valid
  @NotNull
  @JsonProperty
  private String allowedOrigins;

  @Valid
  @NotNull
  @JsonProperty("authentication")
  private MshAuthConfiguration mshAuthConfiguration;

  @Valid
  @NotNull
  @JsonProperty("environment")
  private Environment environment;

  @NonNull
  @JsonProperty("clapi")
  protected ClapiConfiguration clapiConfig;
}
