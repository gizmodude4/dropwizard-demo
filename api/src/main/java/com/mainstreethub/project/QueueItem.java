package com.mainstreethub.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QueueItem {
  public final long businessId;
  public final String jobType;
  public final long queueId;
  public final String sessionId;
  public final String status;
  public final String error;
  public final String executedAt;
  public final String payload;
  public final List<String> screenshots;

  @JsonCreator
  public QueueItem(@JsonProperty("business_id") long businessId,
                   @JsonProperty("job_type") String jobType,
                   @JsonProperty("queue_id") long queueId,
                   @JsonProperty("session_id") String sessionId,
                   @JsonProperty("status") String status,
                   @JsonProperty("error") String error,
                   @JsonProperty("executed_at") String executedAt,
                   @JsonProperty("payload") String payload,
                   @JsonProperty("screenshots") List<String> screenshots) {
    this.businessId = businessId;
    this.jobType = jobType;
    this.queueId = queueId;
    this.sessionId = sessionId;
    this.status = status;
    this.error = error;
    this.executedAt = executedAt;
    this.payload = payload;
    this.screenshots = screenshots;
  }

  @JsonProperty("business_id")
  public long getBusinessId() {
    return businessId;
  }

  @JsonProperty("job_type")
  public String getJobType() {
    return jobType;
  }

  @JsonProperty("queue_id")
  public long getQueueId() {
    return queueId;
  }

  @JsonProperty("session_id")
  public String getSessionId() {
    return sessionId;
  }

  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("error")
  public String getError() {
    return error;
  }

  @JsonProperty("executed_at")
  public String getExecutedAt() {
    return executedAt;
  }

  @JsonProperty("payload")
  public String getPayload() {
    return payload;
  }

  @JsonProperty("screenshots")
  public List<String> getScreenshots() {
    return screenshots;
  }

}
