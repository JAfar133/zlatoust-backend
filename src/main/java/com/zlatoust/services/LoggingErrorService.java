package com.zlatoust.services;

import com.zlatoust.mapper.LoggingEventMapper;
import com.zlatoust.models.LoggingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingErrorService {

    private final LoggingEventMapper loggingEventMapper;

    public int getCountErrorsLastNMinutes(int minutes) {
        long timestamp = getTimestampMinutesAgo(minutes);
        return loggingEventMapper.findErrorsCountFromDate(timestamp);
    }

    public List<LoggingEvent> getErrorsLastNMinutes(int minutes, boolean withTrace) {
        long timestamp = getTimestampMinutesAgo(minutes);
        return withTrace
                ? loggingEventMapper.findErrorsFromDateWithTrace(timestamp)
                : loggingEventMapper.findErrorsFromDate(timestamp);
    }

    public LoggingEvent getLoggingEventById(long id) {
        return loggingEventMapper.findLoggingErrorById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Error event with id = " + id + " wasn't found"));
    }

    private long getTimestampMinutesAgo(int minutes) {
        long nowInMillis = System.currentTimeMillis();
        long minutesInMillis = Duration.ofMinutes(minutes).toMillis();
        return nowInMillis - minutesInMillis;
    }

}