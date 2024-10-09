package com.zlatoust.mapper;


import com.zlatoust.models.LoggingEvent;
import org.springframework.data.repository.query.Param;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Optional;

@Mapper
public interface LoggingEventMapper {

    int findErrorsCountFromDate(@Param("date") long date);

    List<LoggingEvent> findErrorsFromDate(@Param("date") long date);

    List<LoggingEvent> findErrorsFromDateWithTrace(@Param("date") long date);

    List<String> findTraceLines(@Param("event_id") Long errorId);

    Optional<LoggingEvent> findLoggingErrorById(@Param("event_id") Long errorId);

}
