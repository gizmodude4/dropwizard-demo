package com.mainstreethub.contentcalendar.resources;

import com.mainstreethub.contentcalendar.Content;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContentCalendarResource {
  @Inject
  ContentCalendarResource() {
  }

  @GET
  @Path("/posts")
  public Response findUser(@QueryParam("locationId") long locationId,
                           @QueryParam("published") boolean published,
                           @QueryParam("lastSeen") String publishedAfter,
                           @QueryParam("count") long count) {
    Content dummy = Content.builder()
        .caption("dummy")
        .compositionId(UUID.randomUUID())
        .locationId(locationId)
        .mediaId(UUID.randomUUID())
        .status("scheduled")
        .submissionId(Optional.of(UUID.randomUUID()))
        .publishAfter(Optional.of(OffsetDateTime.now()))
        .build();
    return Response.ok(dummy).build();
  }
}
