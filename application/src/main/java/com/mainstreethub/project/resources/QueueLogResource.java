package com.mainstreethub.project.resources;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.mainstreethub.project.QueueItem;
import com.mainstreethub.project.dao.QueueLogDAO;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QueueLogResource {
  private final QueueLogDAO dao;
  private final DateTimeFormatter isoFormat = ISODateTimeFormat.dateTime();
  private final DateTimeFormatter s3DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
  private final AmazonS3Client s3Client = new AmazonS3Client();

  @Inject
  QueueLogResource(QueueLogDAO dao) {
    this.dao = checkNotNull(dao);
  }

  @GET
  @Path("/{job_type}")
  public Response findPastJobs(@PathParam("job_type") String jobType, @QueryParam("business_id") long id) {
    List<QueueLogDAO.QueueLogItem> items = dao.getQueueLogEntries(id, jobType);
    if (items.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity("No jobs found").build();
    }

    List<QueueItem> queueItems = new ArrayList<>();
    ListObjectsV2Result images;
    ListObjectsV2Result payload;


    for (QueueLogDAO.QueueLogItem item : items) {
      String payloadString = "";
      String payloadPrefix = "automaton/payloads/prod/" + item.executedAt.toString(s3DateFormat) +
          "/" + item.queueId + "/" + item.sessionId.replaceAll("[^A-Za-z0-9]","");
      payload = s3Client.listObjectsV2("backups.mainstreethub.com", payloadPrefix);
      for (S3ObjectSummary payloadSummary : payload.getObjectSummaries()){
        S3Object payloadObject = s3Client.getObject(payloadSummary.getBucketName(), payloadSummary.getKey());
        try{
          InputStream gzipStream = new GZIPInputStream(payloadObject.getObjectContent());
          payloadString = IOUtils.toString(gzipStream);
          gzipStream.close();
        }
        catch(IOException exc){
          System.out.println("Couldn't read " + payloadSummary.getKey());
        }
      }
      List<String> screenshotUrls = new ArrayList<>();
      String imagePrefix = "automaton-screenshots/prod/" + item.sessionId;
      images = s3Client.listObjectsV2("backups.mainstreethub.com", imagePrefix);
      for (S3ObjectSummary imageSummary : images.getObjectSummaries()){
        screenshotUrls.add("images/" + imageSummary.getKey());
      }
      queueItems.add(new QueueItem(item.businessId, item.jobType, item.queueId,
          item.sessionId, item.status, item.error, isoFormat.print(item.executedAt),
          payloadString, screenshotUrls));
    }
    return Response.ok(queueItems).header("Access-Control-Allow-Origin", "*").build();
  }
}
