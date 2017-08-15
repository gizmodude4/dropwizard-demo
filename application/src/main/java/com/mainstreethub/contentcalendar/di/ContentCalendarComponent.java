package com.mainstreethub.contentcalendar.di;

import com.mainstreethub.contentcalendar.ContentCalendarModule;
import com.mainstreethub.contentcalendar.resources.ContentCalendarResource;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ContentCalendarModule.class})
public interface ContentCalendarComponent {
  ContentCalendarResource getContentCalendarResource();
}
