package com.bupt.ZigbeeResolution.mapper;

import com.bupt.ZigbeeResolution.data.LoggingEvent;
import org.apache.ibatis.annotations.*;


@Mapper
public interface LogEventMapper {

    @Insert("INSERT INTO logevent (timestmp,formatted_message) VALUES (#{timestmp}, #{formatted_message})")
    @Options(useGeneratedKeys = true, keyProperty = "event_id", keyColumn = "event_id")
    Integer addLogEvent(LoggingEvent loggingEvent);

}
