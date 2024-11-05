package com.zlatoust.exceptions.handler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Object errorObject;

    public ApiError(String path, HttpStatus status, String message, Object errorObject) {
        this(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path, errorObject);
    }

    public ApiError(String path, HttpStatus status, String message) {
        this(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path, null);
    }
}
