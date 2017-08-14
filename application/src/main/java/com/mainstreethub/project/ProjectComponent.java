package com.mainstreethub.project;

import com.mainstreethub.project.dao.JDBIModule;
import com.mainstreethub.project.resources.QueueLogResource;
import com.mainstreethub.project.resources.S3BypassResource;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {JDBIModule.class, ProjectModule.class})
public interface ProjectComponent {
  QueueLogResource getUsersResource();
  S3BypassResource getS3BypassResource();
}
