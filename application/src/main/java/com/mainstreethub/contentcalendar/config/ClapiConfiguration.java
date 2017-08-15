package com.mainstreethub.contentcalendar.config;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
public class ClapiConfiguration {
  @NotEmpty
  protected String endpoint;
  @NotEmpty
  protected String apiKey;
  @NotEmpty
  protected String apiPassword;
}
