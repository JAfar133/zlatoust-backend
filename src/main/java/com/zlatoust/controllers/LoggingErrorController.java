package com.zlatoust.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zlatoust.models.LoggingEvent;
import com.zlatoust.services.LoggingErrorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actuator/error")
@RequiredArgsConstructor
public class LoggingErrorController {

    private final LoggingErrorService loggingErrorService;

    @Value("${app.system.condition.errors.last.minutes}")
    private Integer lastMinutes;

    @GetMapping("/count")
    public ResponseEntity<String> getErrorsCount() {
        int errorsCount = loggingErrorService.getCountErrorsLastNMinutes(lastMinutes);
        return ResponseEntity.ok("Errors count in the last " + lastMinutes / 24 / 60 + " days: " + errorsCount);
    }

    @GetMapping("/stats")
    public ResponseEntity<ErrorsRequest> getErrorsInfo(@RequestParam(required = false) boolean trace) {
        List<LoggingEvent> errors = loggingErrorService.getErrorsLastNMinutes(lastMinutes, trace);
        return ResponseEntity.ok(new ErrorsRequest(
                "Errors in the last " + lastMinutes / 24 / 60 + " days",
                errors
        ));
    }

    @GetMapping("/{error_id}")
    public ResponseEntity<LoggingEvent> getErrorInfo(@PathVariable("error_id") long errorId) {
        return ResponseEntity.ok(loggingErrorService.getLoggingEventById(errorId));
    }

    @AllArgsConstructor
    private static class ErrorsRequest {
        @JsonProperty("message")
        private String message;
        @JsonProperty("errors")
        private List<LoggingEvent> errors;
    }
}
