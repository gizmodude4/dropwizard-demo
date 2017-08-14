package com.mainstreethub.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public interface QueueLogDAO {
  @SqlQuery("SELECT\n" +
      "  ql.business_id as business_id,\n" +
      "  a.class_name as class_name,\n" +
      "  ql.queue_id as queue_id,\n" +
      "  ql.aws_session_id as session_id,\n" +
      "  ql.status as status,\n" +
      "  ql.error as error,\n" +
      "  ql.executed_at as executed_at\n" +
      "FROM\n" +
      "  queue_log as ql\n" +
      "INNER JOIN api_source a\n" +
      "  ON ql.api_source_id=a.id\n" +
      "WHERE\n" +
      "  executed_at > CONVERT_TZ(NOW(),\"UTC\",\"US/Central\") - INTERVAL 30 DAY\n" +
      "  AND business_id = :business_id\n" +
      "  AND class_name = :class_name")
  @Mapper(QueueLogMapper.class)
  List<QueueLogItem> getQueueLogEntries(@Bind("business_id") long business_id,
                                  @Bind("class_name") String class_name);

  static final class QueueLogItem {
    public final long businessId;
    public final String jobType;
    public final long queueId;
    public final String sessionId;
    public final String status;
    public final String error;
    public final DateTime executedAt;

    public QueueLogItem(long businessId, String jobType,
                        long queueId, String sessionId,
                        String status, String error, String executedAtString) {
      this.businessId = businessId;
      this.jobType = jobType;
      this.queueId = queueId;
      this.sessionId = sessionId;
      this.status = status;
      this.error = error;
      this.executedAt = DateTime.parse(executedAtString, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
  }

  static final class QueueLogMapper implements ResultSetMapper<QueueLogItem> {
    @Override
    public QueueLogItem map(int index, ResultSet r, StatementContext ctx) throws SQLException {
      return new QueueLogItem(r.getLong("business_id"), r.getString("class_name"),
          r.getLong("queue_id"), r.getString("session_id"), r.getString("status"),
          r.getString("error"), r.getString("executed_at"));
    }
  }
}
