package com.zlatoust.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class LoggingEvent {
    private Long errorId;
    private LocalDateTime timestmp;
    private String formattedMessage;
    private String loggerName;
    private String levelString;
    private String threadName;
    private Integer referenceFlag;
    private String arg0;
    private String arg1;
    private String arg2;
    private String arg3;
    private String callerFilename;
    private String callerClass;
    private String callerMethod;
    private String callerLine;
    private List<String> traceLines;
}
