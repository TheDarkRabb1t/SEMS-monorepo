package model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import model.dto.LogEventDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Document(indexName = "enriched_log_event")
public class EnrichedLogEvent implements Serializable {

    @Id
    private String id;

    @Field(type = FieldType.Ip)
    private String ip;

    @Field(type = FieldType.Text)
    private String message;

    @Field(type = FieldType.Text, name = "raw_user_agent")
    private String rawUserAgent;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;

    @Field(type = FieldType.Object)
    private GeoLocation geo;

    @Field(type = FieldType.Object, name = "parsed_user_agent")
    private UserAgentInfo parsedUserAgent;

    @Field(type = FieldType.Keyword)
    private SeverityLevel severity;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Object)
    private AuthContext auth;

    public static EnrichedLogEvent fromLogEvent(LogEventDto logEventDto) {
        EnrichedLogEvent enrichedLogEvent = new EnrichedLogEvent();
        enrichedLogEvent.setIp(logEventDto.getIp());
        enrichedLogEvent.setStatus(logEventDto.getStatus());
        enrichedLogEvent.setMessage(logEventDto.getMessage());
        enrichedLogEvent.setRawUserAgent(logEventDto.getUserAgent());
        enrichedLogEvent.setTimestamp(logEventDto.getTimestamp());
        return enrichedLogEvent;
    }
}