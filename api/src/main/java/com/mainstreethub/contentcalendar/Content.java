package com.mainstreethub.contentcalendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
public class Content {
  private long locationId;

  @NonNull
  private String status;

  @NonNull
  private UUID compositionId;

  @NonNull
  private UUID mediaId;

  @NonNull
  private String caption;

  @NonNull
  private Optional<UUID> submissionId;

  @NonNull
  private Optional<OffsetDateTime> publishAfter;

  public Content(long locationId, String status, UUID compositionId, UUID mediaId,
                 String caption, Optional<UUID> submissionId, Optional<OffsetDateTime> publishAfter){
    this.locationId = locationId;
    this.status = status;
    this.compositionId = compositionId;
    this.mediaId = mediaId;
    this.caption = caption;
    this.submissionId = submissionId;
    this.publishAfter = publishAfter;
  }
}
