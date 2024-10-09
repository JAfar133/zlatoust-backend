package com.zlatoust.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ZlatoustException extends RuntimeException {
    private final HttpStatus status;
    public ZlatoustException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ZlatoustException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
