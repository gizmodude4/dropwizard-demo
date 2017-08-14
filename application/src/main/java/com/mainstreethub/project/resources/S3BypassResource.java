package com.mainstreethub.project.resources;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/images")
@Produces("image/png")
public class S3BypassResource {
  AmazonS3Client s3Client = new AmazonS3Client();

  @Inject
  S3BypassResource(){}

  @GET
  @Path("/{image_folder}/{environment}/{filename}")
  public Response getS3Image(@PathParam("image_folder") String imageFolder,
                             @PathParam("environment") String environment,
                             @PathParam("filename") String filename) {
    String imageKey = imageFolder + "/" + environment + "/" + filename;
    if (!s3Client.doesObjectExist("backups.mainstreethub.com", imageKey)) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Couldn't find " + imageKey)
          .header("Access-Control-Allow-Origin", "*")
          .build();
    }
    S3Object payloadObject = s3Client.getObject("backups.mainstreethub.com", imageKey);
    try{
      InputStream imageStream = payloadObject.getObjectContent();
      byte [] image = IOUtils.toByteArray(imageStream);
      imageStream.close();
      return Response.ok(image)
          .header("Access-Control-Allow-Origin", "*")
          .build();
    }
    catch(IOException exc){
      return Response.status(Response.Status.NOT_FOUND)
          .header("Access-Control-Allow-Origin", "*")
          .entity("Couldn't read " + imageKey)
          .build();
    }
  }
}
