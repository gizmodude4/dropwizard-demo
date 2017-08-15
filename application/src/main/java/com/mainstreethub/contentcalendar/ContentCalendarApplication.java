package com.mainstreethub.contentcalendar;

import com.mainstreethub.contentcalendar.di.ContentCalendarComponent;
import com.mainstreethub.contentcalendar.di.DaggerContentCalendarComponent;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.bundles.version.VersionBundle;
import io.dropwizard.bundles.version.VersionSupplier;
import io.dropwizard.bundles.version.suppliers.MavenVersionSupplier;


public class ContentCalendarApplication extends Application<ContentCalendarConfiguration> {
  public static void main(String[] args) throws Exception {
    new ContentCalendarApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<ContentCalendarConfiguration> bootstrap) {
    initVersion(bootstrap);
  }

  @Override
  public void run(ContentCalendarConfiguration configuration, Environment environment) throws Exception {
    ContentCalendarComponent component = DaggerContentCalendarComponent.builder()
        .contentCalendarModule(new ContentCalendarModule())
        .build();

    environment.jersey().register(component.getContentCalendarResource());
  }

  private void initVersion(Bootstrap<ContentCalendarConfiguration> bootstrap) {
      VersionSupplier supplier = new MavenVersionSupplier("com.mainstreethub.contentcalendar", "application");
      bootstrap.addBundle(new VersionBundle(supplier));
  }
}
